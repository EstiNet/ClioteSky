package main

import (
	pb "../protoc"
	"context"
	"google.golang.org/grpc"
	"net"
	"log"
	"fmt"
	"github.com/tucnak/store"
	"os"
	"io/ioutil"
	"github.com/BurntSushi/toml"
	"errors"
	"github.com/nu7hatch/gouuid"
)

var (
	version = "v2.0.0"

	csConfig   CSConfig
	grpcServer *grpc.Server
	tokens     map[string]string //token to name
	requests   map[string][]pb.ClioteMessage

	configPath   = "./config.conf"
	invalidToken = errors.New("invalid authentication token")

	masterKey string
)

//gRPC
type ClioteSkyService struct{}

type Cliote struct {
	Name     string `toml:"name"`
	Category string `toml:"category"`
}

type CSConfig struct {
	Port         string            `toml:"port"`
	MasterKeyLoc string            `toml:"master_key_location"`
	CertFile     string            `toml:"cert_file_path"`
	KeyFile      string            `toml:"key_file_path"`
	Cliotes      map[string]Cliote `toml:"clients"`
}

func init() {
	store.Init("./config")
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

		err = ioutil.WriteFile(configPath, "config.conf", 0644)
		fmt.Println("Created config file!")
	}

	//marshal toml
	if _, err := toml.DecodeFile(configPath, &csConfig); err != nil {
		fmt.Println(err)
		return
	}

	lis, err := net.Listen("tcp", "36000")
	if err != nil {
		log.Fatal("Oh no! IPC listen error (check if the port has been taken):" + err.Error())
	}

	grpcServer = grpc.NewServer()
	pb.RegisterClioteSkyServiceServer(grpcServer, &ClioteSkyService{})
	grpcServer.Serve(lis)
}

// gRPC Implemented Functions

func (clioteskyservice *ClioteSkyService) Request(ctx context.Context, token *pb.Token) (pb.ClioteSkyService_RequestClient, error) {
	user, ok := tokens[token.Token]
	if !ok {
		return stream, invalidToken;
	}
	messages, ok := requests[user]
	if ok {
		return &pb.ClioteResponse{Data: &messages}, nil;
	}
	return nil;
}

func (clioteskyservice *ClioteSkyService) Send(ctx context.Context, send *pb.ClioteSend) (*pb.Empty, error) {
	user, ok := tokens[send.Token]
	if !ok {
		return &pb.Empty{}, invalidToken;
	}
	requests[send.Recipient] = append(requests[send.Recipient], pb.ClioteMessage{Data: send.Data, Identifier: send.Identifier, Sender: user})
	return &pb.Empty{}, nil;
}

func (clioteskyservice *ClioteSkyService) Auth(ctx context.Context, auth *pb.AuthRequest) (*pb.Token, error) {
	if auth.Password == masterKey {
		return &pb.Token{Token: getNewToken(auth.User)}, nil;
	}
	return &pb.Token{}, errors.New("invalid password");
}

func getNewToken(user string) (strToken string) { //TODO token expiry date
	token, err := uuid.NewV4()
	if err != nil {
		log.Fatal("Can't obtain a new token for " + user + " " + err.Error())
	}
	strToken = token.String()
	if _, ok := tokens[strToken]; ok { //get new token if it's already taken
		strToken = getNewToken(user) //hopefully doesn't stack overflow
	}
	tokens[strToken] = user
	return
}
