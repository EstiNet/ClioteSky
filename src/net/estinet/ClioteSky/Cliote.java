package net.estinet.ClioteSky;

public class Cliote {
	private String name = "";
	private String ip = "127.0.0.1";
	public Cliote(String connectionname, String connectip){
		name = connectionname;
		ip = connectip;
	}
	public String getName(){
		return name;
	}
	public String getIP(){
		return ip;
	}
	public void setName(String newname){
		name = newname;
	}
	public void setIP(String newip){
		ip = newip;
	}
}
