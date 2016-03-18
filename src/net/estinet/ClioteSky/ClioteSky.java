package net.estinet.ClioteSky;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import net.estinet.ClioteSky.network.ClioteSocket;
import net.estinet.ClioteSky.network.NetworkUtil;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.OutputPacket;

public class ClioteSky {
	public static String version = "0.0.7-BETA";
	public static State state = State.ENABLING;
	public static boolean exit = true;
	public static long commandid = 0;
	public static int port = 36000;
	public static PublicKey publickey = null;
	public static PrivateKey privatekey = null;
	
	public static List<Cliote> aliveCache = new ArrayList<>();
	
	public static List<ClioteSocket> connections = new ArrayList<>();
	
	public static List<Command> commands = new ArrayList<>();
	public static List<Category> categories = new ArrayList<>();
	
	public static List<InputPacket> inputPackets = new ArrayList<>();
	public static List<OutputPacket> outputPackets = new ArrayList<>();
	
	public static Cliote getCliote(String clioteName){
		for(Category category : categories){
			for(Cliote cliote : category.getCliotes()){
				if(cliote.getName().equals(clioteName)){
					return cliote;
				}
			}
		}
		return null;
	}
	public static void setCliote(Cliote cliote){
		for(int it = 0; it < categories.size(); it++){
			for(int i = 0; i < categories.get(it).getCliotes().size(); i++){
				if(categories.get(it).getCliotes().get(i).getName().equals(cliote.getName())){
					List<Cliote> cliotes = categories.get(it).getCliotes();
					cliotes.set(i, cliote);
					categories.get(it).setCliotes(cliotes);
				}
			}
		}
	}
	public static ClioteSocket getClioteSocket(Cliote cliote){
		for(ClioteSocket cs : connections){
			if(NetworkUtil.getIP(cs.getSocket()).equals(cliote.getIP())){
				return cs;
			}
		}
		return null;
	}
	public static void println(String output){
		System.out.println("[System]: " + output);
	}
	public static void printSignal(String output){
		System.out.println("[TCP]: " + output);
	}
	public static String getPublicKey(){
		String pub = "";
		for(byte b :  ClioteSky.publickey.getEncoded()){
			pub += b;
		}
		return pub;
	}
	public static String getPrivateKey(){
		String pri = "";
		for(byte b : ClioteSky.privatekey.getEncoded()){
			pri += b;
		}
		return pri;
	}
}
