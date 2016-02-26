package net.estinet.ClioteSky;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private String name;
	private List<Cliote> cliotes = new ArrayList<>();
	
	public Category(String categoryname){
		name = categoryname;
	}
	
	public void setName(String newname){
		name = newname;
	}
	public void setCliotes(List<Cliote> newcliotes){
		cliotes = newcliotes;
	}
	public List<Cliote> getCliotes(){
		return cliotes;
	}
	public String getName(){
		return name;
	}
	public void addCliote(Cliote cliote){
		cliotes.add(cliote);
	}
	public void removeCliote(String name){
		for(Cliote c : cliotes){
			if(c.getName().equals(name)){
				cliotes.remove(c);
			}
		}
	}
	public void removeCliote(Cliote cliote){
		cliotes.remove(cliote);
	}
}
