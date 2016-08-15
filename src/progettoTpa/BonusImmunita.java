package progettoTpa;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;


public class BonusImmunita extends GestoreImmagini implements Runnable
{
	private Thread sc;
	private FinestraGioco p;
	private int velocita;
  private boolean gestioneThread;
	public BonusImmunita(FinestraGioco p, int x, int y,int tempo)
	{
		super(new ImageIcon("bonusScudo.png").getImage(),x,y);
		this.velocita=tempo;
		this.p=p;
		 gestioneThread=true;
		this.sc=new Thread(this);
		sc.start();
	}

	public void paint(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		if (this.isVisible())
			g2.drawImage(super.getImage(),super.getX(),super.getY(),p);
		else
		{
			this.sc.interrupt();
		}
	}

	
	public void move() 
	{  
		if(super.getY()<100)
			super.setY(super.getY()+1);
		else if(super.getY()>700){
		
			   this.die();
			   this.setGestioneThread(false);
		   }
		else
			super.setY(super.getY()+1);
	}
	

	 public void setGestioneThread(boolean v){//la uso per interrompere il thread
		 gestioneThread=v;
	}
	
	public void run() 
	{
		try 
		{
			while( gestioneThread)
			{
				
				//p.repaint();
				this.move();
				Thread.sleep(velocita);
			}
		} 
		catch (InterruptedException e) 
		{
			super.setX(-500);
			super.setY(-500);
			return;
		}
	}
}