package net.estinet.ClioteSky.network.protocol;

import java.util.List;

import net.estinet.ClioteSky.Cliote;

public class OutputPacket implements Packet{
	private String name, format, description;
	
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
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public void run(List<String> args, Cliote sender) {
		
	}

}
