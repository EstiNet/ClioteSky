package net.estinet.ClioteSky;

/*
Copyright 2016 EstiNet

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import net.estinet.ClioteSky.audio.MakeSound;
import net.estinet.ClioteSky.commands.ClioteSockets;
import net.estinet.ClioteSky.commands.Cliotes;
import net.estinet.ClioteSky.commands.CreateCategory;
import net.estinet.ClioteSky.commands.Debug;
import net.estinet.ClioteSky.commands.Encrypt;
import net.estinet.ClioteSky.commands.FlushSockets;
import net.estinet.ClioteSky.commands.Help;
import net.estinet.ClioteSky.commands.Key;
import net.estinet.ClioteSky.commands.Stop;
import net.estinet.ClioteSky.configuration.Categories;
import net.estinet.ClioteSky.configuration.Config;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.input.InputAlive;
import net.estinet.ClioteSky.network.protocol.input.InputChange;
import net.estinet.ClioteSky.network.protocol.input.InputCreate;
import net.estinet.ClioteSky.network.protocol.input.InputHello;
import net.estinet.ClioteSky.network.protocol.input.InputSend;
import net.estinet.ClioteSky.network.protocol.output.OutputAlive;
import net.estinet.ClioteSky.network.protocol.output.OutputError;
import net.estinet.ClioteSky.network.protocol.output.OutputMessage;

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
		 * Load Input and Output Streams
		 */
		
		ClioteSky.inputPackets.add(new InputAlive());
		ClioteSky.inputPackets.add(new InputChange());
		ClioteSky.inputPackets.add(new InputCreate());
		ClioteSky.inputPackets.add(new InputHello());
		ClioteSky.inputPackets.add(new InputSend());
		
		ClioteSky.outputPackets.add(new OutputAlive());
		ClioteSky.outputPackets.add(new OutputError());
		ClioteSky.outputPackets.add(new OutputMessage());
		
		/*
		 * Load Serializer
		 */
		
		Categories cat = new Categories();
		cat.load();
		
		/*
		 * Sets up RSA encryption variables
		 */
		
		ObjectInputStream inputStream = null;
	    try {
			inputStream = new ObjectInputStream(new FileInputStream(EncryptionUtil.PUBLIC_KEY_FILE));
			final PublicKey publicKey = (PublicKey) inputStream.readObject();
			ClioteSky.publickey = publicKey;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			inputStream = new ObjectInputStream(new FileInputStream(EncryptionUtil.PRIVATE_KEY_FILE));
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
		ClioteSky.commands.add(new Encrypt());
		ClioteSky.commands.add(new Cliotes());
		ClioteSky.commands.add(new CreateCategory());
		ClioteSky.commands.add(new ClioteSockets());
		ClioteSky.commands.add(new FlushSockets());
		ClioteSky.commands.add(new Debug());
		
		/*
		 * Start CommandSystem
		 */
		
		ClioteSky.println("Starting CommandSystem...");
		
		ClioteSky.state = State.COMMAND;
		
		final CommandSystem cs = new CommandSystem();
		Thread thr = new Thread(new Runnable(){
			public void run(){
				ClioteSky.console.setPrompt(">");
				cs.start();
			}
		});
		thr.start();
		ClioteSky.commandid = thr.getId();
		System.out.println("Welcome to ClioteSky.");
	}
}
