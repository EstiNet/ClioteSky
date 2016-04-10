package net.estinet.ClioteSky;

/*
Copyright 2016 EstiNet

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable{
	/**
	 * Category to store Cliotes
	 */
	private static final long serialVersionUID = 7917049985137261145L;
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
