package net.estinet.ClioteSky.configuration;

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
