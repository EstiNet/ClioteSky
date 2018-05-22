package main

import (
	pb "../protoc"
	"errors"
	"fmt"
	"github.com/BurntSushi/toml"
	"google.golang.org/grpc"
	"io/ioutil"
	"log"
	"net"
	"os"
	"strconv"
	"time"
	"google.golang.org/grpc/credentials"
	"sync"
	"bufio"
	"io"
	"strings"
	"math/rand"
	"context"
)

var (
	version = "v2.0.0"

	csConfig   CSConfig
	grpcServer *grpc.Server
	//tokens     = make(map[string]string) //token to name
	//requests   = make(map[string][]pb.ClioteMessage)
	//cliotes    = make(map[string][]string) //category to name
	tokens    = sync.Map{}
	requests  = sync.Map{}
	cliotes   = sync.Map{}
	lastCheck = sync.Map{} //cliote name to unix timestamp

	configPath   = "./config.conf"
	invalidToken = errors.New("invalid authentication token")

	masterKey string
)

//gRPC
type ClioteSkyService struct{}

type CSConfig struct {
	Port         int64  `toml:"port"`
	MasterKeyLoc string `toml:"master_key_location"`
	CertFile     string `toml:"cert_file_path"`
	KeyFile      string `toml:"key_file_path"`
}

func main() {
	fmt.Println("ClioteSky " + version)

	//Configuration
	_, err := os.Stat(configPath)
	if os.IsNotExist(err) {
		file, err := os.Create(configPath)
		if err != nil {
			log.Fatal(err)
		}
		file.Close()

		err = ioutil.WriteFile(configPath, []byte(defConfig), 0644)
		if err != nil {
			log.Fatal(err)
		}
		fmt.Println("Created config file!")
	}

	fmt.Println("Loading config...")
	//marshal toml
	if _, err := toml.DecodeFile(configPath, &csConfig); err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println("Loaded!")

	//check if master key exists
	_, err = os.Stat(csConfig.MasterKeyLoc)
	if os.IsNotExist(err) {
		fmt.Println("Master key not found, creating new one.")
		masterKey = RandStringBytesMaskSrc(3000)
		err = ioutil.WriteFile(csConfig.MasterKeyLoc, []byte(masterKey), 0644)
		if err != nil {
			log.Fatal(err)
		}
	} else {
		dat, err := ioutil.ReadFile(csConfig.MasterKeyLoc)
		if err != nil {
			log.Fatal(err)
		}
		masterKey = string(dat)
	}

	//start grpc
	lis, err := net.Listen("tcp", ":"+strconv.Itoa(int(csConfig.Port)))
	if err != nil {
		log.Fatal("Oh no! IPC listen error (check if the port has been taken):" + err.Error())
	}

	//ssl
	creds, err := credentials.NewServerTLSFromFile(csConfig.CertFile, csConfig.KeyFile)
	if err != nil {
		log.Fatal("Unable to use tls key files, check the directory. " + csConfig.CertFile + " : " + csConfig.KeyFile + ". Error: " + err.Error())
	}

	go startCommands() //start command line

	go func() {
		time.Sleep(5 * time.Minute)
		purgeOldCliotes()
	}() //start cliote purge task

	grpcServer = grpc.NewServer(grpc.Creds(creds))
	pb.RegisterClioteSkyServiceServer(grpcServer, &ClioteSkyService{})
	fmt.Println("Starting gRPC Server...")
	grpcServer.Serve(lis)
}

/*
 * Purges cliotes that have not requested for data within the last day
 */

func purgeOldCliotes() {
	f := func(key, value interface{}) bool {
		if value.(int64) < time.Now().Unix()-86400 { //one day
			fl := func(key, value interface{}) bool { //remove cliote
				newArr := []string{}
				for _, cliote := range value.([]string) {
					if cliote != key {
						newArr = append(newArr, cliote)
						break
					}
				}
				cliotes.Delete(key)
				cliotes.Store(key, newArr) //TODO check if thread safe
				return true;
			}
			cliotes.Range(fl)
		}
		return true
	}
	lastCheck.Range(f)
}

/*
 * Starts loop that takes in input for commands
 */

func startCommands() {
	var reader = bufio.NewReader(os.Stdin)
	for { //command line loop
		input, err := reader.ReadString('\n')
		if err == io.EOF {
			time.Sleep(100 * time.Millisecond)
			continue
		}
		if err != nil {
			println(err.Error())
		}
		input = strings.TrimRight(input, "\n")
		str := strings.Split(input, " ")
		switch str[0] {
		case "help":
			fmt.Println("-----Help-----")
			fmt.Println("cliotes  | List all of the cliotes.")
			fmt.Println("requests | List all of the requests waiting for cliotes.")
			fmt.Println("tokens   | List the token allocation.")
			fmt.Println("clean    | Removes cliotes that have not connected within a day.")
			break
		case "cliotes":
			fmt.Println("Categories:\n-----------")
			f := func(key, value interface{}) bool {
				fmt.Println(key.(string) + ":")
				for _, cliote := range value.([]string) {
					fmt.Println("  " + cliote)
				}
				return true
			}

			cliotes.Range(f)
			break
		case "requests":
			fmt.Println("Messages:\n----------")
			f := func(key, value interface{}) bool {
				fmt.Println(key.(string) + ":")
				for _, message := range value.([]pb.ClioteMessage) {
					fmt.Println(message.Sender + " " + message.Identifier + " " + string(message.Data))
				}
				return true
			}
			requests.Range(f)
			break
		case "tokens":
			fmt.Println("Tokens:\n----------")
			f := func(key, value interface{}) bool {
				fmt.Println(key.(string) + " : " + value.(string))
				return true
			}
			tokens.Range(f)
			break
		case "clean":
			purgeOldCliotes()
			fmt.Println("Old cliotes purged.")
			break
		}
	}
}

// gRPC Implemented Functions

func (clioteskyservice *ClioteSkyService) Request(token *pb.Token, stream pb.ClioteSkyService_RequestServer) error {
	user, ok := tokens.Load(token.Token)
	if !ok {
		return invalidToken
	}
	messages, ok := requests.Load(user)
	if ok {
		for _, message := range messages.([]pb.ClioteMessage) {
			if err := stream.Send(&message); err != nil {
				return err
			}
		}
		requests.Delete(user)
	}
	return nil
}

func (clioteskyservice *ClioteSkyService) Send(ctx context.Context, send *pb.ClioteSend) (*pb.Empty, error) {
	user, ok := tokens.Load(send.Token)
	if !ok {
		return &pb.Empty{}, invalidToken
	}

	fmt.Println("[INFO] " + user.(string) + " sent " + send.Identifier + " " + string(send.Data) + " to " + send.Recipient + ".");
	f := func(key, value interface{}) bool {
		for _, cliote := range value.([]string) {
			if send.Recipient == "all" || key == send.Recipient || cliote == send.Recipient {
				v, _ := requests.Load(cliote)
				var nv []pb.ClioteMessage
				if v == nil {
					nv = make([]pb.ClioteMessage, 0)
				} else {
					nv = v.([]pb.ClioteMessage)
				}
				requests.Store(cliote, append(nv, pb.ClioteMessage{Data: send.Data, Identifier: send.Identifier, Sender: user.(string)}))
			}
		}
		return true
	}

	cliotes.Range(f)
	return &pb.Empty{}, nil
}

func (clioteskyservice *ClioteSkyService) Auth(ctx context.Context, auth *pb.AuthRequest) (*pb.Token, error) {
	//caveat: users can sign up twice with different categories
	if auth.Password == masterKey {

		if _, ok := cliotes.Load(auth.Category); ok {
			v, _ := cliotes.Load(auth.Category)
			add := true
			for _, val := range v.([]string) {
				if val == auth.User {
					add = false
				}
			}
			if (add) {
				cliotes.Store(auth.Category, append(v.([]string), auth.User))
			}
		} else {
			cliotes.Store(auth.Category, []string{auth.User})
		}

		fmt.Println("[INFO] " + auth.User + " has authenticated.");
		return &pb.Token{Token: getNewToken(auth.User)}, nil
	}
	return &pb.Token{}, errors.New("invalid master key")
}

func (clioteskyservice *ClioteSkyService) CheckNameTaken(ctx context.Context, strin *pb.String) (*pb.Boolean, error) {

	ret := false

	f := func(key, value interface{}) bool {
		for _, cliote := range value.([]string) {
			if cliote == strin.Str {
				ret = true
			}
		}
		return true
	}

	cliotes.Range(f)
	return &pb.Boolean{B: ret}, nil;
}

func getNewToken(user string) (strToken string) {
	strToken = RandStringBytesMaskSrc(100)
	if _, ok := tokens.Load(strToken); ok { //get new token if it's already taken
		strToken = getNewToken(user) //hopefully doesn't stack overflow
	}
	tokens.Store(strToken, user)
	go func() {
		//token expiry
		time.Sleep(time.Hour * 48) //regen every 2 days
		tokens.Delete(strToken)
	}()
	return
}

//Random string generator

var src = rand.NewSource(time.Now().UnixNano())

const letterBytes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
const (
	letterIdxBits = 6                    // 6 bits to represent a letter index
	letterIdxMask = 1<<letterIdxBits - 1 // All 1-bits, as many as letterIdxBits
	letterIdxMax  = 63 / letterIdxBits   // # of letter indices fitting in 63 bits
)

func RandStringBytesMaskSrc(n int) string {
	b := make([]byte, n)
	// A src.Int63() generates 63 random bits, enough for letterIdxMax characters!
	for i, cache, remain := n-1, src.Int63(), letterIdxMax; i >= 0; {
		if remain == 0 {
			cache, remain = src.Int63(), letterIdxMax
		}
		if idx := int(cache & letterIdxMask); idx < len(letterBytes) {
			b[i] = letterBytes[idx]
			i--
		}
		cache >>= letterIdxBits
		remain--
	}

	return string(b)
}

//Default config string
const defConfig = `port = 36000
master_key_location = "./masterkey.key"
cert_file_path = "./server.crt"
key_file_path = "./server.key"`
