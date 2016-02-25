package net.estinet.ClioteSky;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandSystem {
	protected void start(){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.print("> ");
			Scanner scan = new Scanner(System.in);
			String input = scan.nextLine();
			scan.close();
			String[] args = input.split("\\s+");
			List<String> argss = new ArrayList<>();	
			for(String str : args){
				argss.add(str);
			}
			for(Command command : ClioteSky.commands){
				if(command.getName().equalsIgnoreCase(args[0])){
					Thread thr = new Thread(new Runnable(){
						public void run(){
							command.run(argss);
						}
					});
					thr.start();
					if(thr.isAlive()){
						start();
					}
					else{
						Thread thr2 = new Thread(new Runnable(){
							public void run(){
								while(true){
									if(thr.isAlive()){
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
			System.out.println("\n[System]: Command not recognized. :P");
	}
}
