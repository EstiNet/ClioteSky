package net.estinet.ClioteSky.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.network.protocol.Decosion;

public class NetworkUtil {
	public static ServerSocket serverSocket = null;
	public static Socket clientSocket = null;
	public void openTCP(){
		try {	         
			
			serverSocket = new ServerSocket(ClioteSky.port);
			serverSocket.accept();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			Decosion de = new Decosion();

			while (true) {
				try{
					String inputLine = in.readLine();
					ClioteSky.printSignal("Signal recieved from " + clientSocket.getRemoteSocketAddress().toString() + ":" + Integer.toString(clientSocket.getPort()) + " with query " + inputLine);
					boolean done = false;
					String actual = inputLine;//EncryptionUtil.decrypt(inputLine.getBytes(), ClioteSky.privatekey);
					for(Category category : ClioteSky.categories){
						for(Cliote cliote : category.getCliotes()){
							if(cliote.getIsOnline()){
								if(cliote.getIP().equals(clientSocket.getRemoteSocketAddress().toString()) && cliote.getPort().equals(Integer.toString(clientSocket.getPort()))){
									de.decode(actual, cliote);
									done = true;
								}
							}
						}
					}
					if(!done){
						de.decode(actual, new Cliote("unknown", clientSocket.getLocalAddress().getHostAddress(), Integer.toString(clientSocket.getPort())));
					}
				}
				catch(Exception e){
					System.out.println("Oops! Connection exception. :/");
					System.out.println(e.getMessage());
				}
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
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			//ADD ENCRYPTION HERE WHEN READY :D
			out.write(output);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
