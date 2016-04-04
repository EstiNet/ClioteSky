package net.estinet.ClioteSky.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

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
		int close = 0;
		BufferedReader in;
		boolean closes = true;
		while(closes){
			try{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String inputLine = in.readLine();
				if(inputLine == null){
					close++;
				}
				if(close > 1){
					for(int i = 0; i < ClioteSky.categories.size(); i++){
						for(int iter = 0; iter < ClioteSky.categories.get(i).getCliotes().size(); iter++){
							if(ClioteSky.categories.get(i).getCliotes().get(iter).getIP().equals(NetworkUtil.getIP(socket)) && ClioteSky.categories.get(i).getCliotes().get(iter).getPort().equals(Integer.toString(socket.getPort()))){
								ClioteSky.categories.get(i).getCliotes().get(iter).setIsOnline(false);
							}
						}
					}
					ClioteSky.printSignal("Connection closed with " + NetworkUtil.getIP(socket) + ":" + socket.getPort());
					ClioteSky.getConnections().remove(this);
					break;
				}
				else{
					Decosion de = new Decosion();
					ClioteSky.printSignal("Signal recieved from " + NetworkUtil.getIP(socket) + ":" + socket.getPort() +  " with query " + inputLine);
					boolean done = false;
					String actual = inputLine;//EncryptionUtil.decrypt(inputLine.getBytes(), ClioteSky.privatekey);
					for(Category category : ClioteSky.categories){
						for(Cliote cliote : category.getCliotes()){
							if(cliote.getIP().equals(NetworkUtil.getIP(socket)) && cliote.getPort().equals(Integer.toString(socket.getPort()))){
								de.decode(actual, cliote);
								done = true;
								break;
							}
						}
					}
					if(!done){
						de.decode(actual, new Cliote("unknown", NetworkUtil.getIP(socket), Integer.toString(socket.getPort())));
					}
				}
			}
			catch(SocketException e){
				for(int i = 0; i < ClioteSky.categories.size(); i++){
					for(int iter = 0; iter < ClioteSky.categories.get(i).getCliotes().size(); iter++){
						if(ClioteSky.categories.get(i).getCliotes().get(iter).getIP().equals(NetworkUtil.getIP(socket)) && ClioteSky.categories.get(i).getCliotes().get(iter).getPort().equals(Integer.toString(socket.getPort()))){
							ClioteSky.categories.get(i).getCliotes().get(iter).setIsOnline(false);
						}
					}
				}
				ClioteSky.printSignal("Connection closed with " + NetworkUtil.getIP(socket) + ":" + socket.getPort());
				ClioteSky.getConnections().remove(this);
				break;
			}
			catch(ArrayIndexOutOfBoundsException e){
				ClioteSky.printSignal("Connection closed with " + NetworkUtil.getIP(socket) + ":" + socket.getPort());
				closes = false;
			}
			catch(Exception e){
				System.out.println("Oops! Connection exception. :/");
				e.printStackTrace();
			}
		}
	}
}
