package net.estinet.ClioteSky;

import net.estinet.ClioteSky.audio.MakeSound;
import net.estinet.ClioteSky.commands.Help;
import net.estinet.ClioteSky.configuration.Config;

final class Enable {
	protected void enable(){
		/*
		 * ClioteSky Startup Process.
		 */
		System.out.println("Starting ClioteSky version " + ClioteSky.version + "...");
		
		MakeSound ms = new MakeSound();
		ms.play();
		
		/*
		 * Load Configurations
		 */
		Config c = new Config();
		c.setConfig();
		
		/*
		 * Load Commands 
		 */
		
		ClioteSky.commands.add(new Help());
		
		/*
		 * Start CommandSystem
		 */
		
		ClioteSky.state = State.COMMAND;
		
		CommandSystem cs = new CommandSystem();
		cs.start();
	}
}
