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
)

var (
	version = "v2.0.0"

	csConfig CSConfig
	grpcServer *grpc.Server
	tokens map[string]string //token to name

	configPath = "./config.conf"
)

//gRPC
type ClioteSkyService struct{}

type Cliote struct {
	Name string `toml:"name"`
	Category string `toml:"category"`
}

type CSConfig struct {
	Port string `toml:"port"`
	MasterKeyLoc string `toml:"master_key_location"`
	CertFile string `toml:"cert_file_path"`
	KeyFile string `toml:"key_file_path"`
	Cliotes map[string]Cliote `toml:"clients"`
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

		err := ioutil.WriteFile(configPath, d1, 0644)
		fmt.Println("Created config file!")
	}

	//marshal toml

	lis, err := net.Listen("tcp", "36000")
	if err != nil {
		log.Fatal("Oh no! IPC listen error (check if the port has been taken):" + err.Error())
	}
	grpcServer = grpc.NewServer()
	pb.RegisterClioteSkyServiceServer(grpcServer, &ClioteSkyService{})
	grpcServer.Serve(lis)
}


// gRPC Implemented Functions

func (clioteskyservice *ClioteSkyService) Request(ctx context.Context, token *pb.Token) (*pb.ClioteResponse, error) {
	return &pb.ClioteResponse{}, nil;
}

func (clioteskyservice *ClioteSkyService) Send(ctx context.Context, send *pb.ClioteSend) (*pb.Empty, error) {

	return &pb.Empty{}, nil;
}

func (clioteskyservice *ClioteSkyService) Auth(ctx context.Context, auth *pb.AuthRequest) (*pb.Token, error) {
	return &pb.Token{}, nil;
}
