package net.estinet.ClioteSky.network;

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
				clientSocket = serverSocket.accept();
				ClioteSky.printSignal("Initializing connection with " + getIP(clientSocket) + ":" + clientSocket.getPort());
				ClioteSocket newSocket = new ClioteSocket(clientSocket);
				ClioteSky.connections.add(newSocket);
				newSocket.start();
			}
		} catch (IOException e) {
			System.out.println("Oops! Connection exception. :/");
			System.out.println(e.getMessage());
		}
	}
	public void sendOutput(ClioteSocket cliote, String output){
		try{
			DataOutputStream outToServer = new DataOutputStream(cliote.getSocket().getOutputStream());
			//ADD ENCRYPTION HERE WHEN READY :D
			outToServer.writeBytes(output);
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
