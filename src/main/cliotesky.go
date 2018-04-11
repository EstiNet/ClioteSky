package main

import (
	pb "../protoc"
	"context"
	"google.golang.org/grpc"
	"net"
	"log"
	"fmt"
)

var (
	version = "v2.0.0"

	grpcServer *grpc.Server
	tokens map[string]string
)

type ClioteSkyService struct{}

func init() {

}

func main() {
	fmt.Println("ClioteSky " + version)

	lis, err := net.Listen("tcp", "36000")
	if err != nil {
		log.Fatal("Oh no! IPC listen error (check if the port has been taken):" + err.Error())
	}
	grpcServer = grpc.NewServer()
	pb.RegisterClioteSkyServiceServer(grpcServer, &ClioteSkyService{})
	grpcServer.Serve(lis)
}

func (clioteskyservice *ClioteSkyService) Request(ctx context.Context, token *pb.Token) (*pb.ClioteResponse, error) {

}

func (clioteskyservice *ClioteSkyService) Send(ctx context.Context, send *pb.ClioteSend) (*pb.Empty, error) {

	return &pb.Empty{}, nil;
}

func (clioteskyservice *ClioteSkyService) Auth(ctx context.Context, auth *pb.AuthRequest) (*pb.Token, error) {

}
