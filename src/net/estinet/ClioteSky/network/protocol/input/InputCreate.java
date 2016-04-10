package net.estinet.ClioteSky.network.protocol.input;

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

import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.configuration.ImproperClioteException;
import net.estinet.ClioteSky.configuration.SerialUtil;
import net.estinet.ClioteSky.exceptions.ClioteNameAlreadyUsedException301;
import net.estinet.ClioteSky.exceptions.CreationCategoryNotKnownException300;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;
import net.estinet.ClioteSky.network.protocol.output.OutputError;

public class InputCreate extends InputPacket implements Packet {

	public InputCreate(){
		super.setName("create");
		super.setDescription("The create function initializes a Cliote with the ClioteSky installation. This must be sent before any other functions.");
		super.setUsage("create [Cliote Name] [Category] [Password]");
	}

	@Override
	public void run(List<String> args, Cliote sender) {
		if(args.size() != 3){
			try{
				throw new IncorrectArgumentsException101();
			}
			catch(IncorrectArgumentsException101 e){
				e.printStackTrace();
				OutputError oe = new OutputError();
				oe.run(Arrays.asList("101"), sender);
			}
		}
		else{
			String newClioteName = args.get(0), categoryToJoin = args.get(1), password = args.get(2);
			if(ClioteSky.getCliote(newClioteName) != null){
				try{
					throw new ClioteNameAlreadyUsedException301();
				}
				catch(ClioteNameAlreadyUsedException301 e){
					e.printStackTrace();
					OutputError oe = new OutputError();
					oe.run(Arrays.asList("301"), sender);
				}
			}
			else{
				boolean bool = true;
				for(Category category : ClioteSky.categories){
					if(category.getName().equals(categoryToJoin)){
						bool = false;
					}
				}
				if(!bool){
					Cliote cliote = new Cliote(newClioteName, sender.getIP(), sender.getPort(), password);
					for(int i = 0; i<ClioteSky.categories.size(); i++){
						if(ClioteSky.categories.get(i).getName().equals(categoryToJoin)){
							ClioteSky.categories.get(i).addCliote(cliote);
							SerialUtil su = new SerialUtil();
							try {
								su.createCliote(ClioteSky.categories.get(i), cliote, true);
							} catch (ImproperClioteException e) {
								e.printStackTrace();
							}
						}
					}
				}
				else{
					try{
						throw new CreationCategoryNotKnownException300();
					}
					catch(CreationCategoryNotKnownException300 e){
						e.printStackTrace();
						OutputError oe = new OutputError();
						oe.run(Arrays.asList("300"), sender);
					}
				}
			}
		}
	}
}
