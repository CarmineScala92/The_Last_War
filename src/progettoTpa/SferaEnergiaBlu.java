package progettoTpa;


import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;



public class SferaEnergiaBlu  extends GestoreImmagini implements Runnable{

	
	private Thread c;
	private FinestraGioco p;
	
	
	
	public SferaEnergiaBlu(FinestraGioco p, int x, int y)
	{
		super(new ImageIcon("sferaGoku.png").getImage(),x,y);
		this.p=p;
		c=new Thread(this);
		c.start();
	}

	//sempre utile per disegnare l'immagine 
	public void paint(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		if (this.isVisible()){
			//g2.draw(this.getBounds());
			g2.drawImage(super.getImage(),super.getX(),super.getY(),p);
		}
		else
		{
			this.c.interrupt();
			
		}
	}

	//scelgo movimento che deve avere il colpo
	public void move() 
	{  
		if(super.getX()<1200){
			
			super.setX(super.getX()+1);
			}
		else//se esce dal riquadro lo elimino
		{
			super.setX(super.getX());
			this.die();
			
			p.repaint();
			c.interrupt();
		}
	}

	public void run() 
	{
		try 
		{
			while(true)
			{
				p.repaint();
				this.move();
				Thread.sleep(3);
			} 
		}
		catch (InterruptedException e) 
		{
			//e.printStackTrace();
			setX(-500);
			setY(-500);
			return;
		}
	}

	
	
	
	
	
	
	
}
