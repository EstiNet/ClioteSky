package net.estinet.ClioteSky.network;

import java.io.BufferedOutputStream;

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

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import net.estinet.ClioteSky.ClioteSky;

public class NetworkUtil {
	public static ServerSocket serverSocket = null;
	public static Socket clientSocket = null;
	public void openTCP(){
		try {	         
			serverSocket = new ServerSocket(ClioteSky.port);

			while (true) {
				clientSocket = NetworkUtil.serverSocket.accept();
				ClioteSky.printSignal("Initializing connection with " + getIP(clientSocket) + ":" + clientSocket.getPort());
				ClioteSocket newSocket = new ClioteSocket(clientSocket);
				ClioteSky.addClioteSocket(newSocket);
				newSocket.start();
			}
		} catch (IOException e) {
			System.out.println("Oops! Connection exception. :/");
			e.printStackTrace();
		}
	}
	public void sendOutput(ClioteSocket cliote, String output){
		try{
			DataOutputStream outToServer = new DataOutputStream(new BufferedOutputStream(cliote.getSocket().getOutputStream()));
			//ADD ENCRYPTION HERE WHEN READY :D
			outToServer.writeBytes(output + "\n");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String getIP(Socket socket){
		InetSocketAddress sockaddr = (InetSocketAddress)socket.getRemoteSocketAddress();
		InetAddress inaddr = sockaddr.getAddress();
		String ip = "";
		if (inaddr instanceof Inet4Address)
			ip = ((Inet4Address)inaddr).toString();
		else if (inaddr instanceof Inet6Address)
			ip = ((Inet6Address)inaddr).toString();
		return ip;
	}
}
