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
		File f = new File("./Cliotes/" + args.get(1));
		f.mkdir();
		ClioteSky.categories.add(new Category(args.get(1)));
		ClioteSky.println("Created category " + args.get(1));
		}
		catch(Exception e){
			ClioteSky.fprintln("Something is wrong with your input! Try again. :P");
			e.printStackTrace();
		}
	}
}
