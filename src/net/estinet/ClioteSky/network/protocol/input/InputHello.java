package net.estinet.ClioteSky.network.protocol.input;

import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.exceptions.RegisterFirstException901;
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
		if(sender.getName().equals("unknown")){
			try{
				throw new RegisterFirstException901();
			}
			catch(RegisterFirstException901 e){

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

			}
		}
	}
}
