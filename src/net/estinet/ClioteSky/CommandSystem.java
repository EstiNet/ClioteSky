package net.estinet.ClioteSky;

import java.io.Console;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandSystem {
	protected void start(){
		String input = null;
		try {
			input = ClioteSky.console.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		String[] args = input.split("\\s+");
		final List<String> argss = new ArrayList<>();
		for(String str : args){
			argss.add(str);
		}
		boolean lel = true;
		for(final Command command : ClioteSky.commands){
			if(command.getName().equalsIgnoreCase(args[0])){
				lel = false;
				final Thread thr = new Thread(new Runnable(){
					public void run(){
						command.run(argss);
					}
				});
				thr.start();
				if(!thr.isAlive()){
					start();
					break;
				}
				else{
					Thread thr2 = new Thread(new Runnable(){
						public void run(){
							while(true){
								if(!thr.isAlive()){
									start();
									break;
								}
								else{
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}
					});
					thr2.start();
				}
			}
		}
		if(lel){
			System.out.println("\n[System]: Command not recognized. :P");
			start();
		}
	}
}
