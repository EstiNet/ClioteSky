package net.estinet.ClioteSky.network.protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.exceptions.FunctionNotKnownException100;
import net.estinet.ClioteSky.network.protocol.output.OutputError;

public class Decosion {
	public void decode(String message, Cliote sender){
		boolean lol = true;
		String[] args = message.split("\\s+");
		for(InputPacket packet : ClioteSky.inputPackets){
			if(args[0].equalsIgnoreCase(packet.getName())){
				List<String> newArgs = new ArrayList<>();
				for(int i = 1; i<args.length; i++){
					newArgs.add(args[i]);
				}
				packet.run(newArgs, sender);
				lol = false;
			}
		}
		if(lol){
			try{
				throw new FunctionNotKnownException100();
			}
			catch(FunctionNotKnownException100 e){
				e.printStackTrace();
				OutputError oe = new OutputError();
				oe.run(Arrays.asList("100"), sender);
			}
		}
	}
}
