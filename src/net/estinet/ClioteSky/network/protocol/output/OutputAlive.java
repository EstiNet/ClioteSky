package net.estinet.ClioteSky.network.protocol.output;

import java.util.List;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.OutputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class OutputAlive extends OutputPacket implements Packet{

	public OutputAlive(){
		super.setName("alive");
		super.setDescription("This function is sent to the client after the client sends an alive packet to ensure that the server is marked online.");
		super.setFormat("alive");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		NetworkUtil nu = new NetworkUtil();
		nu.sendOutput(ClioteSky.getClioteSocket(sender), "alive");
		ClioteSky.printSignal("Responded alive with " + NetworkUtil.getIP(ClioteSky.getClioteSocket(sender).getSocket()) + ":" + ClioteSky.getClioteSocket(sender).getSocket().getPort());
	}

}
