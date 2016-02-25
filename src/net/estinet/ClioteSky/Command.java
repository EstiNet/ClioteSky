package net.estinet.ClioteSky;

import java.util.List;

public class Command {
	private String name = "";
	private String desc = "";
	public void run(List<String> args){}
	public String getName(){
		return name;
	}
	public String getDescription(){
		return desc;
	}
	public void setName(String newname){
		name = newname;
	}
	public void setDescription(String newdesc){
		desc = newdesc;
	}
}
