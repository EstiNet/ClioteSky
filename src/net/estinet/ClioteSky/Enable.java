package net.estinet.ClioteSky;

import net.estinet.ClioteSky.configuration.Config;

final class Enable {
	protected void enable(){
		/*
		 * ClioteSky Startup Process.
		 */
		System.out.println("Starting ClioteSky version " + ClioteSky.version + "...");
		
		/*
		 * Load Configurations
		 */
		Config c = new Config();
		c.setConfig();
	}
}
