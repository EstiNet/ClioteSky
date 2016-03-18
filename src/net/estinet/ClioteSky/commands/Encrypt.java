package net.estinet.ClioteSky.commands;

import java.util.List;

import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;
import net.estinet.ClioteSky.EncryptionUtil;

public class Encrypt extends Command{
	public Encrypt(){
		super.setName("Encrypt");
		super.setDescription("Encrypts the given query with the public key.");
	}
	@Override
	public void run(List<String> args){
		String query = "";
		for(int i = 1; i < args.size(); i++){
			query += args.get(i) + " ";
		}
		    final byte[] cipherText = EncryptionUtil.encrypt(query, ClioteSky.publickey);
			System.out.println(query);
			System.out.println(new String(cipherText)); 
	}
}
