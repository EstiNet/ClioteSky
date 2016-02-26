package net.estinet.ClioteSky;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class ClioteSky {
	public static String version = "0.0.3-BETA";
	public static State state = State.ENABLING;
	public static boolean exit = true;
	public static long commandid = 0;
	public static int port = 36000;
	public static PublicKey publickey = null;
	public static PrivateKey privatekey = null;
	
	public static List<Command> commands = new ArrayList<>();
	public static List<Category> categories = new ArrayList<>();
	
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
