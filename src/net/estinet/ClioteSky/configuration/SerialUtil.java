package net.estinet.ClioteSky.configuration;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;

public class SerialUtil {
	public void createCliote(Category category, Cliote cliote, boolean createNew) throws ImproperClioteException{
		if(!ClioteSky.categories.contains(category)){
			throw new ImproperClioteException();
		}
		else{
			if(createNew){
				for(int i = 0; i < ClioteSky.categories.size(); i++){
					if(ClioteSky.categories.get(i).equals(category)){
						ClioteSky.categories.get(i).addCliote(cliote);
					}
				}
			}
			File f = new File("./Cliotes/" + category.getName() + "/" + cliote.getName() + ".properties");
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ClioteSky.println("Created Cliote " + cliote.getName() + ".");
			Properties prop = new Properties();
			OutputStream output = null;
			InputStream input = null;
			try {
				input = new FileInputStream(f.getPath());
				prop.load(input);
				input.close();
				output = new FileOutputStream(f.getPath());
				// set the properties value
				prop.setProperty("name", cliote.getName());
				prop.setProperty("ip", cliote.getIP());
				prop.setProperty("port", cliote.getPort());
				prop.setProperty("password", cliote.getPassword());
				// save properties to project root folder
				prop.store(output, null);

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}
}
