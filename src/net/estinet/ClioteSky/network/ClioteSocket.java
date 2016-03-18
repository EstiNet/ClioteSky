package net.estinet.ClioteSky.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.network.protocol.Decosion;

public class ClioteSocket extends Thread{
	private Socket socket;
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public ClioteSocket(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
    	try{
    		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			Decosion de = new Decosion();
			String inputLine = in.readLine();
			ClioteSky.printSignal("Signal recieved from " + socket.getRemoteSocketAddress().toString() + " with query " + inputLine);
			boolean done = false;
			String actual = inputLine;//EncryptionUtil.decrypt(inputLine.getBytes(), ClioteSky.privatekey);
			for(Category category : ClioteSky.categories){
				for(Cliote cliote : category.getCliotes()){
					if(cliote.getIsOnline()){
						if(cliote.getIP().equals(socket.getRemoteSocketAddress().toString()) && cliote.getPort().equals(Integer.toString(socket.getPort()))){
							de.decode(actual, cliote);
							done = true;
						}
					}
				}
			}
			if(!done){
				de.decode(actual, new Cliote("unknown", socket.getLocalAddress().getHostAddress(), Integer.toString(socket.getPort())));
			}
		}
		catch(Exception e){
			System.out.println("Oops! Connection exception. :/");
			System.out.println(e.getMessage());
		}
    }
}
