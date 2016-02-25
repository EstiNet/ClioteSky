package net.estinet.ClioteSky;

import net.estinet.ClioteSky.audio.MakeSound;
import net.estinet.ClioteSky.commands.Help;
import net.estinet.ClioteSky.commands.Stop;
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
		
		ClioteSky.println("Loading configurations...");
		Config c = new Config();
		c.setConfig();
		c.loadConfig();
		
		/*
		 * Startup Listener 
		 */
		
		/*
		 * Load Commands 
		 */
		
		ClioteSky.println("Loading command objects...");
		ClioteSky.commands.add(new Help());
		ClioteSky.commands.add(new Stop());
		
		/*
		 * Start CommandSystem
		 */
		
		ClioteSky.println("Starting CommandSystem...");
		
		ClioteSky.state = State.COMMAND;
		
		CommandSystem cs = new CommandSystem();
		Thread thr = new Thread(new Runnable(){
			public void run(){
				cs.start();
			}
		});
		thr.start();
		ClioteSky.commandid = thr.getId();
		System.out.println("Welcome to ClioteSky.");
	}
}
