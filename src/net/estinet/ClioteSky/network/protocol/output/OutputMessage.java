package net.estinet.ClioteSky.network.protocol.output;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.network.protocol.OutputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class OutputMessage extends OutputPacket implements Packet {

	public OutputMessage(){
		super.setName("message");
		super.setDescription("This function is sent to a client or a group of clients after a client sends a signal to the Category or specific Cliote.");
		super.setFormat("message [Category Recieved From] [Cliote Name Recieved From] [String]");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		
	}

}
