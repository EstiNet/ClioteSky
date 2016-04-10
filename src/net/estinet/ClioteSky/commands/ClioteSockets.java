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
import net.estinet.ClioteSky.network.ClioteSocket;
import net.estinet.ClioteSky.network.NetworkUtil;

public class ClioteSockets extends Command{
	public ClioteSockets(){
		super.setName("ClioteSockets");
		super.setDescription("Lists all the ClioteSockets loaded along with their information.");
	}
	@Override
	public void run(List<String> args){
		for(ClioteSocket cs : ClioteSky.getConnections()){
			System.out.println("ClioteSocket IP: " + NetworkUtil.getIP(cs.getSocket()) + ":" + cs.getSocket().getPort());
		}
	}
}
