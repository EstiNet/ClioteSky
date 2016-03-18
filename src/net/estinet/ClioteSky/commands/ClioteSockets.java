package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;
import net.estinet.ClioteSky.network.NetworkUtil;

public class ClioteSockets extends Command{
	public ClioteSockets(){
		super.setName("ClioteSockets");
		super.setDescription("Lists all the ClioteSockets loaded along with their information.");
	}
	@Override
	public void run(List<String> args){
		for(Category category : ClioteSky.categories){
			for(Cliote cliote : category.getCliotes()){
				System.out.println("ClioteSocket IP: " + NetworkUtil.getIP(ClioteSky.getClioteSocket(cliote).getSocket()) + ":" + ClioteSky.getClioteSocket(cliote).getSocket().getPort());
			}
		}
	}
}
