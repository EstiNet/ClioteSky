package net.estinet.ClioteSky.network.protocol;

import net.estinet.ClioteSky.ClioteSky;

public class Decosion {
	public void decode(String message){
		String[] args = message.split("\\s+");
		for(InputPacket packet : ClioteSky.inputPackets){
			if(args[0].equalsIgnoreCase(packet.getName())){
				
			}
		}
	}
}
