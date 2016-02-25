package net.estinet.ClioteSky.audio;
import java.io.File;

import javax.sound.sampled.*;

public class MakeSound {
	
	public void play(){
	try {
	    AudioInputStream stream;
	    AudioFormat format;
	    DataLine.Info info;
	    Clip clip;
	    //ClassLoader CLDR = this.getClass().getClassLoader();
	    File file = new File("./startup.wav"/*CLDR.getResource("./startup.wav").getFile()*/);
	    stream = AudioSystem.getAudioInputStream(file);
	    format = stream.getFormat();
	    info = new DataLine.Info(Clip.class, format);
	    clip = (Clip) AudioSystem.getLine(info);
	    clip.open(stream);
	    clip.start();
	    Thread.sleep(6000);
	    clip.close();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	}
}