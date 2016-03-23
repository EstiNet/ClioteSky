package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;
import net.estinet.ClioteSky.network.ClioteSocket;
import net.estinet.ClioteSky.network.NetworkUtil;

public class ClioteSockets extends Command{
	public ClioteSockets(){
		super.setName("ClioteSockets");
		super.setDescription("Lists all the ClioteSockets loaded along with their information.");
	}
	@Override
	public void run(List<String> args){
		for(ClioteSocket cs : ClioteSky.getConnections()){
			System.out.println("ClioteSocket IP: " + NetworkUtil.getIP(cs.getSocket()) + ":" + cs.getSocket().getPort());
		}
	}
}
