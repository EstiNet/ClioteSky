package net.estinet.ClioteSky;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandSystem {
	protected void start(){
		while(ClioteSky.exit){
			System.out.println(">");
			Scanner scan = new Scanner(System.in);
			String input = scan.nextLine();
			scan.close();
			String[] args = input.split("\\s+");
			for(Command command : ClioteSky.commands){
				if(command.getName().equals(args[0])){
					List<String> argss = new ArrayList<>();
					for(String str : args){
						argss.add(str);
					}
					command.run(argss);
				}
			}
		}
	}
}
