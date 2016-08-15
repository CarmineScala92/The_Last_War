package progettoTpa;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound extends AudioGame implements Runnable {

	private Thread s;
	private String nameS;
	
	public Sound(String nameS)
	{
		this.nameS=nameS;
		s=new Thread(this);
		s.start();
	}
	
	
	
	public void run() 
	{
		try {
			playSound(this.nameS);
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
