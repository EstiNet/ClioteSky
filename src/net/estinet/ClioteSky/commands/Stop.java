package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.Command;
import net.estinet.ClioteSky.Disable;

public class Stop extends Command{
	public Stop(){
		super.setName("Stop");
		super.setDescription("Stops this instance of ClioteSky.");
	}
	@Override
	public void run(List<String> args){
		Disable disable = new Disable();
		disable.stop();
	}
}
