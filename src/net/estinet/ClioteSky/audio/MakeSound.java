package net.estinet.ClioteSky.audio;
import java.io.File;

import javax.sound.sampled.*;
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

public class MakeSound {
	
	public void play(){
	try {
	    AudioInputStream stream;
	    AudioFormat format;
	    DataLine.Info info;
	    final Clip clip;
	    File file = new File("./startup.wav");
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