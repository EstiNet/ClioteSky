package net.estinet.ClioteSky.network.protocol.input;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class InputAlive extends InputPacket implements Packet {

	public InputAlive(){
		super.setName("alive");
		super.setDescription("Common \"keep-alive\" signals sent to the client must be responded with an \"alive\" function. If the message is not responded within 5 seconds, the Cliote will be marked as offline.");
		super.setUsage("alive");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		if(args.size() > 0){
			try{
				throw new IncorrectArgumentsException101();
			}
			catch(IncorrectArgumentsException101 e){
				e.printStackTrace();
			}
		}
		else{
			
		}
	}

}
