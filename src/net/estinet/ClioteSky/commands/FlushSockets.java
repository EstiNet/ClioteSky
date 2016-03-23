package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;
import net.estinet.ClioteSky.network.ClioteSocket;
import net.estinet.ClioteSky.network.NetworkUtil;

public class FlushSockets extends Command{
	public FlushSockets(){
		super.setName("FlushSockets");
		super.setDescription("Deletes duplicate sockets.");
	}
	@Override
	public void run(List<String> args){
		for(ClioteSocket cliote : ClioteSky.getConnections()){
			for(int i = 0; i < ClioteSky.getConnections().size(); i++){
				ClioteSocket cs = ClioteSky.getConnections().get(i);
				if(cs.getSocket().getPort() == cliote.getSocket().getPort() && NetworkUtil.getIP(cs.getSocket()).equals(NetworkUtil.getIP(cliote.getSocket()))){
					ClioteSky.getConnections().get(i).interrupt();
					ClioteSky.getConnections().remove(i);
				}
			}
		}
	}
}
