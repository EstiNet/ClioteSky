package net.estinet.ClioteSky.network.protocol.output;

import java.util.List;

import net.estinet.ClioteSky.Category;
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
		nu.sendOutput(sender, "alive");
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
					for(Category category : ClioteSky.categories){
						for(Cliote cliote : category.getCliotes()){
							if(cliote.getName().equals(sender.getName())){
								
							}
						}
					}
				}
			}
		});
		thr.start();
	}

}
