package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;

public class Debug extends Command{
	public Debug(){
		super.setName("Debug");
		super.setDescription("Toggles debug messages. And the annoying alive signals.");
	}
	@Override
	public void run(List<String> args){
		if(ClioteSky.debug){
			ClioteSky.debug = false;
			ClioteSky.println("Debug messages are now off.");
		}
		else{
			ClioteSky.debug = true;
			ClioteSky.println("Debug messages are now on.");
		}
	}
}
