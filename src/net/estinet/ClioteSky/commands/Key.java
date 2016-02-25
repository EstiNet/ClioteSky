package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;

public class Key extends Command{
	public Key(){
		super.setName("Key");
		super.setDescription("Prints the public and private key.");
	}
	@Override
	public void run(List<String> args){
		System.out.println("-----<Help>-----");
		for(Command command : ClioteSky.commands){
			System.out.println(command.getName() + " : " + command.getDescription());
		}
	}
}
