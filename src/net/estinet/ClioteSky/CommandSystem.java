package net.estinet.ClioteSky;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandSystem {
	Scanner scan = new Scanner(System.in);
	protected void start(){
		System.out.print("\n>");
		String input = scan.nextLine();
		String[] args = input.split("\\s+");
		List<String> argss = new ArrayList<>();	
		for(String str : args){
			argss.add(str);
		}
		boolean lel = true;
		for(Command command : ClioteSky.commands){
			if(command.getName().equalsIgnoreCase(args[0])){
				lel = false;
				Thread thr = new Thread(new Runnable(){
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
