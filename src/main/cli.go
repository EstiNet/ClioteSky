package main

import (
	pb "../protoc"
	"strings"
)

type Hub struct {
	Name string
}

var (
	hubs []Hub
)

func csReceive(send *pb.ClioteSend) {
	switch send.Identifier {
	case "mg start":
	case "mg stop":
	case "mg add":
	case "mg requestid":
	case "hub add": // hub add [name]
		hubs = append(hubs, Hub{Name: string(send.Data)})
	case "hub remove":
		index := -1
		for i, hub := range hubs {
			if hub.Name == string(send.Data) {
				index = i
				break
			}
		}
		if index != -1 {
			hubs = append(hubs[:index], hubs[index+1:]...)
		}
	case "hub join": // hub join [name]
		// sends hub join [name] [server] back
		// for now it's random
		name := strings.Fields(string(send.Data))[0]
		server := strings.Fields(string(send.Data))[1]
		send := pb.ClioteSend{Recipient: "Bungee", Identifier: "hub join", Data: []byte(name + " " + server)}
		csSend(send)
	case "hub list":
	}
}

func csSend(send pb.ClioteSend) {
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
				requests.Store(cliote, append(nv, pb.ClioteMessage{Data: send.Data, Identifier: send.Identifier, Sender: "cliotesky"}))
			}
		}
		return true
	}

	cliotes.Range(f)
}
