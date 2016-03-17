package net.estinet.ClioteSky.network.protocol;

public class OutputPacket implements Packet{
	private String name, usage;
	
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
	public void run() {
		
	}

}
