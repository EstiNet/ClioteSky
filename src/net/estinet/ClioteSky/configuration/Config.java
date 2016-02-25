package net.estinet.ClioteSky.configuration;

import java.io.File;
import java.io.IOException;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.EncryptionUtil;

public class Config {
	public void setConfig(){
		/*
		 * File check if exists.
		 */
		File f = new File("./Data");
		File file = new File("./Data/config.properties");
		File rsa = new File("./RSA");
		File rsapub = new File("./RSA/public.key");
		File rsapri = new File("./RSA/private.key");
		if(!f.isDirectory()){
			ClioteSky.println("Creating file ./Data");
			f.mkdir();
		}
		if(!rsa.isDirectory()){
			ClioteSky.println("Creating file ./RSA");
			rsa.mkdir();
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
}
