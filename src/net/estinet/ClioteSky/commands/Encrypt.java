package net.estinet.ClioteSky.commands;

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
