package net.estinet.ClioteSky.configuration;

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

import java.io.File;
import java.io.IOException;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.EncryptionUtil;

public class Config {
	File f = new File("./Data");
	File file = new File("./Data/config.properties");
	File connections = new File("./Cliotes");
	File defaultcon = new File("./Cliotes/Default");
	File rsa = new File("./RSA");
	File rsapub = new File("./RSA/public.key");
	File rsapri = new File("./RSA/private.key");
	public void setConfig(){
		/*
		 * File check if exists.
		 */
		
		if(!f.isDirectory()){
			ClioteSky.println("Creating directory ./Data");
			f.mkdir();
		}
		if(!rsa.isDirectory()){
			ClioteSky.println("Creating directory ./RSA");
			rsa.mkdir();
		}
		if(!connections.isDirectory()){
			ClioteSky.println("Creating directory ./Cliotes");
			connections.mkdir();
		}
		if(!defaultcon.isDirectory()){
			ClioteSky.println("Creating directory ./Cliotes/Default");
			defaultcon.mkdir();
		}
		if(!file.exists()){
			try {
				ClioteSky.println("Creating file ./Data/config.properties");
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!rsapub.exists()){
			try {
				ClioteSky.println("Creating file ./RSA/public.key");
				rsapub.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!rsapri.exists()){
			try {
				ClioteSky.println("Creating file ./RSA/private.key");
				rsapri.createNewFile();
				EncryptionUtil.generateKey();
				//Assume that if they don't have the public key they don't have the private key :P
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PropertiesUtil pu = new PropertiesUtil();
		pu.createFile(file);
		
	}
	public void loadConfig(){
		/*
		 * Load configuration (config.properties)
		 */
		
		PropertiesUtil pu = new PropertiesUtil();
		pu.loadFile(file);
		
	}
}
