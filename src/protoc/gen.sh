#!/usr/bin/env bash
export PATH=$PATH:$GOPATH/bin
protoc -I protoc protoc/cliotesky.proto --go_out=plugins=grpc:protoc
