package progettoTpa;
import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
 //
public class AudioGame {
        public static void playSound(String ns) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File f;
        
        switch(ns)
        {
        	case "goku_senzacolpi":
        	{
        		f = new File("goku_senzacolpi.wav");
        		break;
        	}
        	case "goku_spara":
        	{
        		f = new File("goku_spara.wav");
        		break;
        	}
        	case "RitaglioVittoria":
        	{
        		f = new File("RitaglioVittoria.wav");
        		break;
        	}
        	case "end":
        	{
        		f = new File("end.wav");
        		break;
        	}
        	default:
        	{
        		f=null;
        		break;
        	}
        }
                
        AudioInputStream audio = AudioSystem.getAudioInputStream(f);
        AudioFormat format;
        format = audio.getFormat();
        SourceDataLine auline;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        auline = (SourceDataLine) AudioSystem.getLine(info);
        auline.open(format);
        auline.start();
        int nBytesRead = 0;
        //byte[] abData = new byte[524288];
        byte[] abData = new byte[524288];
        while (nBytesRead != -1) 
        {
            nBytesRead = audio.read(abData, 0, abData.length);
            if (nBytesRead >= 0) 
            {
                auline.write(abData, 0, nBytesRead);
            }
        }
        }
        
}

