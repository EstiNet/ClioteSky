package net.estinet.ClioteSky.network.protocol;

import java.util.ArrayList;
import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;

public class Decosion {
	public void decode(String message, Cliote sender){
		String[] args = message.split("\\s+");
		for(InputPacket packet : ClioteSky.inputPackets){
			System.out.println("Looking packets " + packet.getName());
			if(args[0].equalsIgnoreCase(packet.getName())){
				System.out.println("Found!");
				List<String> newArgs = new ArrayList<>();
				for(int i = 1; i>args.length; i++){
					newArgs.add(args[i]);
				}
				packet.run(newArgs, sender);
			}
		}
	}
}
