package net.estinet.ClioteSky.network.protocol.input;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class InputHello extends InputPacket implements Packet {

	public InputHello(){
		super.setName("hello");
		super.setDescription("When a client initialized with the server and is online, this function must be sent before anything else to mark the Cliote as \"online\".");
		super.setUsage("hello [Cliote Name] [Password]");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		
	}

}
