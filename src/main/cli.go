package main

import (
	pb "../protoc"
)

func csReceive(send *pb.ClioteSend) {
	switch send.Identifier {
	case "mg start":
	case "mg stop":
	case "mg add":
	case "mg requestid":
	}
}

func csSend() {

}
