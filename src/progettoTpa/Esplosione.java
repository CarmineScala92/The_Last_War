package progettoTpa;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;


public class Esplosione extends GestoreImmagini  implements Runnable{
	
	private Thread b;
	private FinestraGioco p;
	
	
	
	public Esplosione(FinestraGioco p, int x, int y)
	{
		super(new ImageIcon("collisioneSfere.png").getImage(), x, y);
		this.p=p;
		b=new Thread(this);
		b.start();
	}

	public void paint(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		if (this.isVisible())
			g2.drawImage(super.getImage(),super.getX(),super.getY(),p);
		else
		{
			this.b.interrupt();
			
		}
	}

	public void move() 
	{  
		
	}

	public void run() 
	{
		try 
		{
			while(true)
			{
				p.repaint();
				Thread.sleep(1);
			} 
		}
		catch (InterruptedException e) 
		{
			
			//e.printStackTrace();
			return;
		}
	}


}
