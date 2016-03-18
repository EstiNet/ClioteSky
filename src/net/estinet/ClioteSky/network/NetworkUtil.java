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
import net.estinet.ClioteSky.EncryptionUtil;
import net.estinet.ClioteSky.network.protocol.Decosion;

public class NetworkUtil {
	public void openTCP(){
		 try ( 
		      ServerSocket serverSocket = new ServerSocket(ClioteSky.port);
		      Socket clientSocket = serverSocket.accept();
			  PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) 
		 {	         
	            String inputLine, outputLine;
	            
	            Decosion de = new Decosion();
	            
	            while ((inputLine = in.readLine()) != null) {
	            	try{
	            		ClioteSky.printSignal("Signal recieved from " + clientSocket.getLocalAddress() + ":" + Integer.toString(clientSocket.getPort()));
	            		boolean done = false;
		                String actual = EncryptionUtil.decrypt(inputLine.getBytes(), ClioteSky.privatekey);
		                for(Category category : ClioteSky.categories){
		                	for(Cliote cliote : category.getCliotes()){
		                		if(cliote.getIsOnline()){
		                			if(cliote.getIP().equals(clientSocket.getLocalAddress().getHostAddress()) && cliote.getPort().equals(Integer.toString(clientSocket.getPort()))){
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
}
