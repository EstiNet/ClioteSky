package net.estinet.ClioteSky.network.protocol.output;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.OutputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class OutputError extends OutputPacket implements Packet {

	public OutputError(){
		super.setName("error");
		super.setDescription("This function is sent to a client if there is an error with a function sent to the ClioteSky installation.");
		super.setFormat("error [Code]");
	}

	@Override
	public void run(List<String> args, Cliote sender) {
		try{
			NetworkUtil nu = new NetworkUtil();
			System.out.println("[Error] Error code " + args.get(0) + " being sent to " + NetworkUtil.getIP(ClioteSky.getClioteSocket(sender).getSocket()) + ":" + ClioteSky.getClioteSocket(sender).getSocket().getPort());
			nu.sendOutput(ClioteSky.getClioteSocket(sender), "error " + args.get(0));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
