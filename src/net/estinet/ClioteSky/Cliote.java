package net.estinet.ClioteSky;

public class Cliote {
	/*
	 * Connection Object
	 */
	private String name = "";
	private String ip = "127.0.0.1";
	private String port = "36500";
	private boolean isonline = false;
	public Cliote(String connectionname, String connectip, String conport){
		name = connectionname;
		ip = connectip;
		port = conport;
	}
	public String getName(){
		return name;
	}
	public String getIP(){
		return ip;
	}
	public String getPort(){
		return port;
	}
	public boolean getIsOnline(){
		return isonline;
	}
	public void setName(String newname){
		name = newname;
	}
	public void setIP(String newip){
		ip = newip;
	}
	public void setPort(String newport){
		port = newport;
	}
	public void setIsOnline(boolean isonstate){
		isonline = isonstate;
	}
}
