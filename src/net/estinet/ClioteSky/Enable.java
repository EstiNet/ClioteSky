package net.estinet.ClioteSky;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import net.estinet.ClioteSky.audio.MakeSound;
import net.estinet.ClioteSky.commands.Help;
import net.estinet.ClioteSky.commands.Key;
import net.estinet.ClioteSky.commands.Stop;
import net.estinet.ClioteSky.configuration.Config;
import net.estinet.ClioteSky.network.NetworkUtil;

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
		 * Sets up RSA encryption variables
		 */
		
		ObjectInputStream inputStream = null;
	    try {
			inputStream = new ObjectInputStream(new FileInputStream(EncryptionUtil.PUBLIC_KEY_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			final PublicKey publicKey = (PublicKey) inputStream.readObject();
			ClioteSky.publickey = publicKey;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			inputStream = new ObjectInputStream(new FileInputStream(EncryptionUtil.PRIVATE_KEY_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			ClioteSky.privatekey = privateKey;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		/*
		 * Startup Listener 
		 */
		Thread thr1 = new Thread(new Runnable(){
		public void run(){
		ClioteSky.println("Opening socket listeners...");
		NetworkUtil nu = new NetworkUtil();
		nu.openTCP();
		}
		});
		thr1.start();
		
		/*
		 * Load Commands 
		 */
		
		ClioteSky.println("Loading command objects...");
		ClioteSky.commands.add(new Help());
		ClioteSky.commands.add(new Stop());
		ClioteSky.commands.add(new Key());
		
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
