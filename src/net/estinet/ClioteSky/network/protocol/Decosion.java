package net.estinet.ClioteSky.network.protocol;

/*
Copyright 2016 EstiNet

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

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
