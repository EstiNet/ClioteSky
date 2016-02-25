package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.Command;

public class Help extends Command{
	public Help(){
		super.setName("Help");
		super.setDescription("Lists all commands and descriptions.");
	}
	@Override
	public void run(List<String> args){
		
	}
}
