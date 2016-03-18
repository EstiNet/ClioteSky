package net.estinet.ClioteSky.network.protocol.output;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.OutputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class OutputError extends OutputPacket implements Packet {

	public OutputError(){
		super.setName("error");
		super.setDescription("This function is sent to a client if there is an error with a function sent to the ClioteSky installation.");
		super.setFormat("error [Code]");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		try{
		NetworkUtil nu = new NetworkUtil();
		switch(Integer.parseInt(args.get(0))){
		case 100:
			nu.sendOutput(sender, "100");
			break;
		case 101:
			nu.sendOutput(sender, "101");
			break;
		case 200:
			nu.sendOutput(sender, "200");
			break;
		case 201:
			nu.sendOutput(sender, "201");
			break;
		case 202:
			nu.sendOutput(sender, "202");
			break;
		case 900:
			nu.sendOutput(sender, "900");
			break;
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
