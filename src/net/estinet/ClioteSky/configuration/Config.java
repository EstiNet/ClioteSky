package net.estinet.ClioteSky.configuration;

import java.io.File;
import java.io.IOException;

import net.estinet.ClioteSky.EncryptionUtil;

public class Config {
	public void setConfig(){
		/*
		 * File check if exists.
		 */
		File f = new File("./Data");
		File file = new File("./Data/config.dat");
		File rsa = new File("./RSA");
		File rsapub = new File("./RSA/public.key");
		File rsapri = new File("./RSA/private.key");
		if(!f.isDirectory()){
			f.mkdir();
		}
		if(!rsa.isDirectory()){
			rsa.mkdir();
		}
		if(!file.exists()){
			try {
				file.createNewFile();
				EncryptionUtil.generateKey();
				//Assume that if they don't have the public key they don't have the private key :P
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!rsapub.exists()){
			try {
				rsapub.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!rsapri.exists()){
			try {
				rsapri.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
