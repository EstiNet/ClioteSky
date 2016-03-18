package net.estinet.ClioteSky.commands;

import java.io.File;
import java.util.List;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;

public class CreateCategory extends Command{
	public CreateCategory(){
		super.setName("CreateCategory");
		super.setDescription("Creates a category with the given argument");
	}
	@Override
	public void run(List<String> args){
		try{
		File f = new File("./Cliotes/" + args.get(0));
		f.mkdir();
		ClioteSky.categories.add(new Category(args.get(0)));
		}
		catch(Exception e){
			System.out.println("Something is wrong with your input! Try again. :P");
			e.printStackTrace();
		}
	}
}
