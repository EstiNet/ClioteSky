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

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.Command;

public class Cliotes extends Command{
	public Cliotes(){
		super.setName("Cliotes");
		super.setDescription("Lists all the cliotes loaded along with their information.");
	}
	@Override
	public void run(List<String> args){
		for(Category category : ClioteSky.categories){
			ClioteSky.fprintln("Cliotes under category " + category.getName() + ":");
			for(Cliote cliote : category.getCliotes()){
				ClioteSky.fprintln("Cliote " + cliote.getName() + ":");
				ClioteSky.fprintln("IP: " + cliote.getIP() + ":" + cliote.getPort());
				ClioteSky.fprintln("IsOnline: " + Boolean.toString(cliote.getIsOnline()));
			}
		}
	}
}
