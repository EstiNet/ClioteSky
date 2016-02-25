package net.estinet.ClioteSky.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
		                String actual = EncryptionUtil.decrypt(inputLine, ClioteSky.privatekey);
	            		de.decode(actual);
		                // Responds to the client out.println(outputLine);
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
