package progettoTpa;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public class SferaEnergiaDrago extends GestoreImmagini implements Runnable {

	private Thread c;
	private FinestraGioco p;
	private boolean var;
	public SferaEnergiaDrago(FinestraGioco p, int x, int y)
	{
		super(new ImageIcon("sfera.png").getImage(),x,y);
		this.p=p;
		var=true;
		c=new Thread(this);
		c.start();
	}
	
	
	public void paint(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		if (this.isVisible())
			g2.drawImage(super.getImage(),super.getX(),super.getY(),p);//65 e 12 fanno pariter il colpo dal centro della navicella
		else
		{
			this.c.interrupt();
			
		}
	}
	
	public void run() {
		try 
		{
			while(var)
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

	
	public void setVar(boolean var) {
		this.var = var;
	}


	public void move() {
		if(super.getX()>10)
			super.setX(super.getX()-1);
		else//se esce dal riquadro lo elimino
		{
			super.setX(super.getX());
			this.die();
			p.repaint();
			c.interrupt();
		}
		
	}
	

}
