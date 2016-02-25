package net.estinet.ClioteSky.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtil {
	protected void createFile(File f){

		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(f.getPath());

			// set the properties value
			if(prop.getProperty("port").equals(null)){
				prop.setProperty("port", "26806");
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
}
