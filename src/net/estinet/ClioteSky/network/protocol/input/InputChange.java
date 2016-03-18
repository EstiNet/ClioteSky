package net.estinet.ClioteSky.network.protocol.input;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class InputChange extends InputPacket implements Packet {

	public InputChange(){
		super.setName("change");
		super.setDescription("The change function changes a Cliote's setting. The IP of the client will be changed as well as the category. The name cannot be changed.");
		super.setUsage("change [Cliote Name] [Category] [Password]");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		
	}

}
