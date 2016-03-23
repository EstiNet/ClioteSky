package net.estinet.ClioteSky.network.protocol.input;

import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.configuration.Categories;
import net.estinet.ClioteSky.exceptions.AlreadyLoggedInException400;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.exceptions.NameNotKnownException201;
import net.estinet.ClioteSky.exceptions.PasswordIncorrectException900;
import net.estinet.ClioteSky.exceptions.RegisterFirstException901;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;
import net.estinet.ClioteSky.network.protocol.output.OutputError;

public class InputHello extends InputPacket implements Packet {

	public InputHello(){
		super.setName("hello");
		super.setDescription("When a client initialized with the server and is online, this function must be sent before anything else to mark the Cliote as \"online\".");
		super.setUsage("hello [Cliote Name] [Password]");
	}

	@Override
	public void run(List<String> args, Cliote sender) {
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
			if(ClioteSky.getCliote(args.get(0)) == null){
				try{
					throw new NameNotKnownException201();
				}
				catch(NameNotKnownException201 e){
					e.printStackTrace();
					OutputError oe = new OutputError();
					oe.run(Arrays.asList("201"), sender);
				}
			}
			else{
				if(!ClioteSky.getCliote(args.get(0)).getPassword().equals(args.get(1))){
					try{
						throw new PasswordIncorrectException900();
					}
					catch(PasswordIncorrectException900 e){
						e.printStackTrace();
						OutputError oe = new OutputError();
						oe.run(Arrays.asList("900"), sender);
					}
				}
				else{
					if(ClioteSky.getCliote(args.get(0)).getIsOnline()){
						try{
							throw new AlreadyLoggedInException400();
						}
						catch(AlreadyLoggedInException400 e){
							e.printStackTrace();
							OutputError oe = new OutputError();
							oe.run(Arrays.asList("400"), sender);
						}
					}
					else{
						ClioteSky.getCliote(args.get(0)).setIsOnline(true);
						ClioteSky.getCliote(args.get(0)).setIP(NetworkUtil.getIP(ClioteSky.getClioteSocket(sender).getSocket()));
						ClioteSky.getCliote(args.get(0)).setPort(Integer.toString(ClioteSky.getClioteSocket(sender).getSocket().getPort()));
						Categories cat = new Categories();
						cat.flush(ClioteSky.getCliote(args.get(0)), false);
						ClioteSky.println("Cliote " + args.get(0) + " has logged into ClioteSky.");
					}
				}
			}
		}
	}
}
