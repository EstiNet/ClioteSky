package net.estinet.ClioteSky.network.protocol.output;

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

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.OutputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;

public class OutputAlive extends OutputPacket implements Packet{

	public OutputAlive(){
		super.setName("alive");
		super.setDescription("This function is sent to the client after the client sends an alive packet to ensure that the server is marked online.");
		super.setFormat("alive");
	}
	
	@Override
	public void run(List<String> args, Cliote sender) {
		NetworkUtil nu = new NetworkUtil();
		nu.sendOutput(ClioteSky.getClioteSocket(sender), "alive");
		ClioteSky.printSignal("Responded alive with " + NetworkUtil.getIP(ClioteSky.getClioteSocket(sender).getSocket()) + ":" + ClioteSky.getClioteSocket(sender).getSocket().getPort());
	}

}
