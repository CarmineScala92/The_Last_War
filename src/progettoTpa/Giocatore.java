package progettoTpa;




import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public  class Giocatore  extends GestoreImmagini implements KeyListener,Runnable {

	
	private final static int STARTX=0; //Coordiata x da cui parte il player
	private final static int STARTY=250; //Coordiata y da cui parte il player
	
	private FinestraGioco p;
	private Thread s;
	private boolean fired;
	private String username;
	private int punteggio;
	private int sfereEnergia;
    private ArrayList<SferaEnergiaBlu> colpi;
    private long startTime;
    private Sound so;
	public Giocatore(FinestraGioco p,String username)
	{
		
		super(new ImageIcon("player2.png").getImage(),STARTX,STARTY);
		this.username=username;
		this.punteggio=0;
		this.sfereEnergia=5;//munizioni iniziali
		colpi=new ArrayList<SferaEnergiaBlu>();
		fired=false;
		
		this.p=p;
		 
		s=new Thread(this);
		s.start();
	}
	
	
	public void addPunteggio(String level, int m /* 0 = drago piccolo , 1 = drago grande*/ )
	{
		if(level.compareTo("Facile")==0 && m == 0)
		{
			punteggio=punteggio+(10*1);
		}
		else if (level.compareTo("Medio")==0 && m == 0)
		{
			punteggio=punteggio+(10*5);
		}
		else if (level.compareTo("Difficile")==0 && m == 0)
		{
			punteggio=punteggio+(10*10);
		}
		else if(level.compareTo("Facile")==0 && m == 1)
		{
			punteggio=punteggio+(25*2);
		}
		else if (level.compareTo("Medio")==0 && m == 1)
		{
			punteggio=punteggio+(25*4);
		}
		else if (level.compareTo("Difficile")==0 && m == 1)
		{
			punteggio=punteggio+(25*8);
		}
		
			
	}
	
	public int getPunteggio()
	{
		return this.punteggio;
	}
	
	//le voglio usare epr farle uscire come indicatore 
	public int getSfereEnergia() {
		return sfereEnergia;
	}


	public void setSfereEnergia(int sfereEnergia) {
		this.sfereEnergia = sfereEnergia;
	}


	//disegno l'immagine nel pannello
	public void paint(Graphics g)
	{
		int i;
		Graphics2D g2=(Graphics2D)g;
		if (this.isVisible())
			{
			//g2.draw(this.getBounds());
			g2.drawImage(this.getImage(), this.getX(), this.getY(),p);
			
			}
		else
		{
			this.s.interrupt();
			
		}
		
		if(fired)
		{
			for(i=0;i<colpi.size();i++)
			{
			
				if (colpi.get(i).getX()<1900)
				{
						colpi.get(i).paint(g2);//chiamo la funzione che mi disegna la sfera e intanto il trhed che muove la sfera è in movimento e mi cambia anche le cordinate
				}
				else{
					colpi.remove(i);//Rimuovo il colpo quando esce dall'area di gioco
					
					
				}
			}
		}
		
		
	}
	
	
	//gestisco il movimento quando premo le frecce
	 public void keyPressed(KeyEvent e) 
     {
		 int key=e.getKeyCode();//ti da il valore del tasto premuto(poi confronto per vedere di chi si tratta)
			
		 
		 if (key==KeyEvent.VK_SPACE)
		 {
			if(this.isVisible() && (this.sfereEnergia>0))
			{	
				fire();
				fired=true;
				so=new Sound("goku_spara");//suono dello sparo 
				this.sfereEnergia--;
				this.setSfereEnergia(sfereEnergia);
				//System.out.println("Sparo");
			}
			else
			{
				if(this.sfereEnergia==0)
					//System.out.println("colpi finiti");
					so=new Sound("goku_senzacolpi");//suono munizioni esaurite
			}
		 }
		 
		 
		 
				if(key==KeyEvent.VK_LEFT)
				{
					super.setDx(-1);
					super.setDy(0);
				}
				if(key==KeyEvent.VK_RIGHT)
				{
					super.setDx(1);
					super.setDy(0);
				}
				if(key==KeyEvent.VK_DOWN)
				{
					super.setDy(1);
					super.setDx(0);
				}
				if(key==KeyEvent.VK_UP)
				{
					super.setDy(-1);
					super.setDx(0);
				}
     }
	 
	 //quando rilascio il tasto
	public void keyReleased(KeyEvent e) 
	{
		int key=e.getKeyCode();
		
		
		
		
		if(key==KeyEvent.VK_LEFT)
		{
			super.setDx(0);
		}
		if(key==KeyEvent.VK_RIGHT)
		{
			super.setDx(0);
		}
		if(key==KeyEvent.VK_DOWN)
		{
			super.setDy(0);
		}
		if(key==KeyEvent.VK_UP)
		{
			super.setDy(0);
		}
	}
	
	
	
	public void move()
	{
	if (super.getX()<0)
			super.setX(0);
		else
			if(super.getX()>450)
				super.setX(449);//l'immagine non va oltre i 400
			else
				super.setX(super.getX()+super.getDx());
		
		if(super.getY()<0)    //controllo margini top
			super.setY(0);
		else
		{
			if(super.getY()>640)//controllo margini bottom
				super.setY(640);
			else
				super.setY(super.getY()+super.getDy());
		}
	 }
	
	public void fire()
	 {
		 colpi.add(new SferaEnergiaBlu(p,super.getX()+50,super.getY()+30));//metto i colpi in un arraylist di colpi...e creo il colpo con le cordinate x e y del giocatore 
	 }
	
	
	 public void run() 
	 {
	 	while(true)
		{
			p.repaint();
			this.move();
			try 
			{
				Thread.sleep(5);
			} 
			catch (InterruptedException e) 
			{
				//e.printStackTrace();
				
				this.die();
				p.repaint();
				return;
			}
		}
	  }

	 public ArrayList<SferaEnergiaBlu> getColpi()
	 {
		 return this.colpi;
	 }
	 
	 public boolean getFired()
	 {
		 return this.fired;
	 }
	 
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
