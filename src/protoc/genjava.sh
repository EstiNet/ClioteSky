#!/usr/bin/env bash
export PATH=$PATH:$GOPATH/bin
protoc -I protoc protoc/cliotesky.proto --plugin=protoc-gen-grpc-java=protoc/cliotesky --grpc-java_out=protoc
