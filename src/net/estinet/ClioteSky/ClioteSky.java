package net.estinet.ClioteSky;

import java.util.ArrayList;
import java.util.List;

public class ClioteSky {
	public static String version = "0.0.1 BETA";
	public static State state = State.ENABLING;
	public static boolean exit = true;
	List<Command> commands = new ArrayList<>();
	public static void println(String output){
		System.out.println("[System]: " + output);
	}
	public static void printSignal(String output){
		System.out.println("[TCP]: " + output);
	}
}
