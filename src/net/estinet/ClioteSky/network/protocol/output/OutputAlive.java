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
		super.setDescription("This function is sent to the client every 10 seconds to ensure that the client is \"online\". The client must respond back with an \"alive\" function within 5 seconds, or the Cliote will be marked as offline.");
		super.setFormat("alive");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		NetworkUtil nu = new NetworkUtil();
		nu.sendOutput(ClioteSky.getClioteSocket(sender), "alive");
		Thread thr = new Thread(new Runnable(){
			public void run(){
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(ClioteSky.aliveCache.contains(sender)){
					ClioteSky.aliveCache.remove(sender);
				}
				else{
					Cliote cliote = ClioteSky.getCliote(sender.getName());
					cliote.setIsOnline(false);
					ClioteSky.setCliote(cliote);
				}
			}
		});
		thr.start();
	}

}
