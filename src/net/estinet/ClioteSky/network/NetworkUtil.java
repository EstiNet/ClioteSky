package net.estinet.ClioteSky.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;

public class NetworkUtil {
	public static ServerSocket serverSocket = null;
	public static Socket clientSocket = null;
	public void openTCP(){
		try {	         
			
			serverSocket = new ServerSocket(ClioteSky.port);

			while (true) {
				clientSocket = serverSocket.accept();
				ClioteSky.printSignal("Initializing connection with " + clientSocket.getRemoteSocketAddress().toString());
				ClioteSocket newSocket = new ClioteSocket(clientSocket);
				ClioteSky.connections.add(newSocket);
				newSocket.start();
			}
		} catch (IOException e) {
			System.out.println("Oops! Connection exception. :/");
			System.out.println(e.getMessage());
		}
	}
	public void closeTCP(){

	}
	public void sendOutput(Cliote cliote, String output){
		try{
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			//PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			//ADD ENCRYPTION HERE WHEN READY :D
			outToServer.writeBytes(output);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
