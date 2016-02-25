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
	    Thread thr = new Thread(new Runnable(){
	    	public void run(){
	    		try {
					Thread.sleep(clip.getMicrosecondLength());
					 clip.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    });
	   thr.start();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	}
}