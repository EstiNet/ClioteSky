package net.estinet.ClioteSky.network.protocol.input;

import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.exceptions.RegisterFirstException901;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;
import net.estinet.ClioteSky.network.protocol.output.OutputError;

public class InputSend extends InputPacket implements Packet {

	public InputSend(){
		super.setName("send");
		super.setDescription("The send function sends a string to a category of Cliotes or a single Cliote.");
		super.setUsage("send [Cliote Name or Category to be sent to] [String]");
	}

	@Override
	public void run(List<String> args, Cliote sender) {
		if(sender.getName().equals("unknown") || !sender.getIsOnline()){
			try{
				throw new RegisterFirstException901();
			}
			catch(RegisterFirstException901 e){
				e.printStackTrace();
				OutputError oe = new OutputError();
				oe.run(Arrays.asList("901"), sender);
			}
		}
		else{
			if(args.size() != 2){
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
				String query = "", sentTo = args.get(0);
				for(int i = 1; i < args.size(); i++){
					query += args.get(i);
				}
				if(sentTo.equalsIgnoreCase("all")){
					for(Category category : ClioteSky.categories){
						for(Cliote cliote : category.getCliotes()){
							if(cliote.getIsOnline()){
								
							}
						}
					}
				}
				else{
					
				}
				//Make sure you check for all variable devin
			}
		}
	}

}
