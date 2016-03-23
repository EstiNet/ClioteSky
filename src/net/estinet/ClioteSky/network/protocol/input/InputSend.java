package net.estinet.ClioteSky.network.protocol.input;

import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.exceptions.CategoryAndNameNotKnownException202;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.exceptions.RegisterFirstException901;
import net.estinet.ClioteSky.network.NetworkUtil;
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
			if(args.size() < 2){
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
					query += " " + args.get(i);
				}
				if(sentTo.equalsIgnoreCase("all")){
					for(Category category : ClioteSky.categories){
						for(Cliote cliote : category.getCliotes()){
							if(cliote.getIsOnline()){
								NetworkUtil nu = new NetworkUtil();
								nu.sendOutput(ClioteSky.getClioteSocket(cliote), "message " + ClioteSky.getClioteCategory(sender).getName() + " " + sender.getName() + query);
							}
						}
					}
				}
				else{
					boolean notDone = true;
					for(Category category : ClioteSky.categories){
						if(category.getName().equals(sentTo)){
							for(Cliote cliote : category.getCliotes()){
								if(cliote.getIsOnline()){
									NetworkUtil nu = new NetworkUtil();
									nu.sendOutput(ClioteSky.getClioteSocket(cliote), "message " + ClioteSky.getClioteCategory(sender).getName() + " " + sender.getName() + query);
									notDone = false;
								}
							}
							break;
						}
					}
					if(!(ClioteSky.getCliote(sentTo) == null)){
						NetworkUtil nu = new NetworkUtil();
						nu.sendOutput(ClioteSky.getClioteSocket(ClioteSky.getCliote(sentTo)), "message " + ClioteSky.getClioteCategory(sender).getName() + " " + sender.getName() + query);
						notDone = false;
					}
					if(notDone){
						try{
							throw new CategoryAndNameNotKnownException202();
						}
						catch(CategoryAndNameNotKnownException202 e){
							e.printStackTrace();
							OutputError oe = new OutputError();
							oe.run(Arrays.asList("202"), sender);
						}
					}
				}
			}
		}
	}

}
