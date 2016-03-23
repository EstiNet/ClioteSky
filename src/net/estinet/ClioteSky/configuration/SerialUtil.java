package net.estinet.ClioteSky.configuration;

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
