package net.estinet.ClioteSky.network.protocol;

public class Decosion {
	public void decode(String message){
		String[] args = message.split("\\s+");
		switch(args[0]){
		case "create": 
			break;
		case "change": 
			break;
		case "send":
			break;
		case "alive":
			break;
		case "hello":
			break;
		}
	}
}
