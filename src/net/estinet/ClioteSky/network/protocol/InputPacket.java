package net.estinet.ClioteSky.network.protocol;

import java.util.List;

import net.estinet.ClioteSky.Cliote;

public class InputPacket implements Packet{
	private String name, usage, description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public void run(List<String> args, Cliote sender) {
		
	}

}
