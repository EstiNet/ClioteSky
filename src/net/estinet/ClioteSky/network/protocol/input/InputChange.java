package net.estinet.ClioteSky.network.protocol.input;

import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.exceptions.RegisterFirstException901;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;
import net.estinet.ClioteSky.network.protocol.output.OutputError;

public class InputChange extends InputPacket implements Packet {

	public InputChange(){
		super.setName("change");
		super.setDescription("The change function changes a Cliote's setting. The IP of the client will be changed as well as the category. The name cannot be changed.");
		super.setUsage("change [Cliote Name] [Category] [Password]");
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

			}
		}
	}

}
