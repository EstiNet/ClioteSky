package net.estinet.ClioteSky.network.protocol.input;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class InputCreate extends InputPacket implements Packet {

	public InputCreate(){
		super.setName("create");
		super.setDescription("The create function initializes a Cliote with the ClioteSky installation. This must be sent before any other functions.");
		super.setUsage("create [Cliote Name] [Category] [Password]");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {

	}

}
