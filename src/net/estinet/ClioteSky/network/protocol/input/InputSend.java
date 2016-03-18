package net.estinet.ClioteSky.network.protocol.input;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class InputSend extends InputPacket implements Packet {

	public InputSend(){
		super.setName("send");
		super.setDescription("The send function sends a string to a category of Cliotes or a single Cliote.");
		super.setUsage("send [Cliote Name or Category to be sent to] [String]");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		
	}

}
