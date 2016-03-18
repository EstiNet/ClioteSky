package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;

public class Cliotes extends Command{
	public Cliotes(){
		super.setName("Cliotes");
		super.setDescription("Lists all the cliotes loaded along with their information.");
	}
	@Override
	public void run(List<String> args){
		for(Category category : ClioteSky.categories){
			System.out.println("Cliotes under category " + category.getName() + ":");
			for(Cliote cliote : category.getCliotes()){
				System.out.println("Cliote " + cliote.getName() + ":");
				System.out.println("IP: " + cliote.getIP() + ":" + cliote.getPort());
				System.out.println("IsOnline: " + Boolean.toString(cliote.getIsOnline()));
			}
		}
	}
}
