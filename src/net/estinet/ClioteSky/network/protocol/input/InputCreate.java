package net.estinet.ClioteSky.network.protocol.input;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class InputCreate extends InputPacket implements Packet {

	public InputAlive(){
		super.setName("Alive");
		super.setUsage("Common \"keep-alive\" signals sent to the client must be responded with an \"alive\" function. If the message is not responded within 5 seconds, the Cliote will be marked as offline.");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {

	}

}
