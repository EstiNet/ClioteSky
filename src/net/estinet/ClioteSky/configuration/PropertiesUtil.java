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

import net.estinet.ClioteSky.ClioteSky;

public class PropertiesUtil {
	protected void createFile(File f){

		Properties prop = new Properties();
		OutputStream output = null;
		InputStream input = null;
		try {
			input = new FileInputStream(f.getPath());
			prop.load(input);
			input.close();
			output = new FileOutputStream(f.getPath());
			// set the properties value
			if(!prop.containsKey("port")){
				ClioteSky.println("Creating field port...");
				prop.setProperty("port", "36000");
			}
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
	protected void loadFile(File f){

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(f.getPath());

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			try{
				ClioteSky.port = Integer.parseInt(prop.getProperty("port"));
				ClioteSky.println("Loaded setting \"port\" with value " + prop.getProperty("port"));
			}
			catch(Exception e){
				System.out.println("Something wrong with the port input. Check it? :P");
				e.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
