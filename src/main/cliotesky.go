package main

import (
	pb "../protoc"
	"context"
	"errors"
	"fmt"
	"github.com/BurntSushi/toml"
	"google.golang.org/grpc"
	"io/ioutil"
	"log"
	"net"
	"os"
	"strconv"
	"math/rand"
	"time"
	"google.golang.org/grpc/credentials"
)

var (
	version = "v2.0.0"

	csConfig   CSConfig
	grpcServer *grpc.Server
	tokens     = make(map[string]string) //token to name
	requests   = make(map[string][]pb.ClioteMessage)
	cliotes    = make(map[string][]string) //category to name

	configPath   = "./config.conf"
	invalidToken = errors.New("invalid authentication token")

	masterKey string
)

//gRPC
type ClioteSkyService struct{}

type CSConfig struct {
	Port         int64             `toml:"port"`
	MasterKeyLoc string            `toml:"master_key_location"`
	CertFile     string            `toml:"cert_file_path"`
	KeyFile      string            `toml:"key_file_path"`
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

	grpcServer = grpc.NewServer(grpc.Creds(creds))
	pb.RegisterClioteSkyServiceServer(grpcServer, &ClioteSkyService{})
	fmt.Println("Starting gRPC Server...")
	grpcServer.Serve(lis)
}

// gRPC Implemented Functions

func (clioteskyservice *ClioteSkyService) Request(token *pb.Token, stream pb.ClioteSkyService_RequestServer) error {
	user, ok := tokens[token.Token]
	if !ok {
		return invalidToken
	}
	messages, ok := requests[user]
	if ok {
		for _, message := range messages {
			if err := stream.Send(&message); err != nil {
				return err
			}
		}
		requests[user] = make([]pb.ClioteMessage, 0);
	}
	return nil
}

func (clioteskyservice *ClioteSkyService) Send(ctx context.Context, send *pb.ClioteSend) (*pb.Empty, error) {
	user, ok := tokens[send.Token]
	if !ok {
		return &pb.Empty{}, invalidToken
	}

	fmt.Println("[INFO] " + user + " sent " + string(send.Data) + " to " + send.Recipient + ".");

	for key, category := range cliotes {
		for _, cliote := range category {
			if send.Recipient == "all" || key == send.Recipient || cliote == send.Recipient {
				requests[cliote] = append(requests[cliote], pb.ClioteMessage{Data: send.Data, Identifier: send.Identifier, Sender: user})
			}
		}
	}
	return &pb.Empty{}, nil
}

func (clioteskyservice *ClioteSkyService) Auth(ctx context.Context, auth *pb.AuthRequest) (*pb.Token, error) {
	//caveat: users can sign up twice with different categories
	if auth.Password == masterKey {

		if v, ok := cliotes[auth.Category]; ok {
			v = append(v, auth.User)
		} else {
			cliotes[auth.Category] = []string{auth.User}
		}

		fmt.Println("[INFO] " + auth.User + " has authenticated.");
		return &pb.Token{Token: getNewToken(auth.User)}, nil
	}
	return &pb.Token{}, errors.New("invalid master key")
}

func (clioteskyservice *ClioteSkyService) CheckNameTaken(ctx context.Context, string *pb.String) (*pb.Boolean, error) {
	for _, category := range cliotes {
		for _, cliote := range category {
			if cliote == string.Str {
				return &pb.Boolean{B: true}, nil;
			}
		}
	}
	return &pb.Boolean{B: false}, nil;
}

func getNewToken(user string) (strToken string) { //TODO token expiry date
	strToken = RandStringBytesMaskSrc(100)
	if _, ok := tokens[strToken]; ok { //get new token if it's already taken
		strToken = getNewToken(user) //hopefully doesn't stack overflow
	}
	tokens[strToken] = user
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
