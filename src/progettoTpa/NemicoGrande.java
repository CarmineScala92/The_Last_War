package progettoTpa;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class NemicoGrande extends GestoreImmagini implements Runnable{

	
	private boolean var;
	
	public NemicoGrande (FinestraGioco p, int x, int y,int velocita,int frequenzaSparo)
	{
		super(new ImageIcon("Nemicomaggiore.png").getImage(),x,y);
		this.velocita=velocita;
		this.k=-1;//ci serve per gestire il movimento
		this.p=p;
		var=true;
		flag=false;
		variabile=false;
		this.frequenzaSparo=frequenzaSparo;
		startTime = System.currentTimeMillis();
		energia=new ArrayList<SferaEnergiaDrago>();
		//avvio thread che fa muovere il nemico
		this.o=new Thread(this);
		o.start();
	}

	public void paint(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		if (this.isVisible())
			g2.drawImage(super.getImage(),super.getX(),super.getY(),p);
		else
		{

			this.o.interrupt();
		}


		if(variabile){

			for(int i=0;i<energia.size();i++)
			{

				if (energia.get(i).getX()>10)
				{
					energia.get(i).paint(g2);//chiamo la funzione che mi disegna la sfera e intanto il trhed che muove la sfera � in movimento e mi cambia anche le cordinate
				}
				else{
					energia.remove(i);//Rimuovo il colpo quando esce dall'area di gioco
				}
			}



		}

	}

	public void fire()
	{

		energia.add(new SferaEnergiaDrago(p,super.getX(),super.getY()));//metto i colpi in un arraylist di colpi...e creo il colpo con le cordinate x e y del giocatore 
	}

	public void gestisciEnergiaMostro(){

		long seconds = (System.currentTimeMillis() - startTime) / 1000;
		if(seconds==frequenzaSparo)
		{

			if(this.isVisible())
			{	
				fire();
				variabile=true;
				startTime=System.currentTimeMillis();
			}


		}

	}

	public void run() {
		try 
		{

			while(var)
			{
				gestisciEnergiaMostro();
				//p.repaint();
				this.move();
				Thread.sleep(velocita);//20 attuale primo 20, secondo 5 terzo 3
			}
		} 
		catch (InterruptedException e) 
		{
			return;
		}


	}


	public void setVar(boolean var) {
		this.var = var;
	}

	public void move() {
		int scendi=-1;
		int sali=1;

if(super.getY()<=5){
			
			super.setY(super.getY()+sali);
			flag=false;
		}
		else if(super.getY()>=640){
			
			super.setY(super.getY()+scendi);
			flag=true;
		}
		else{
			
				if(!flag){
					super.setY(super.getY()+sali);
				
				}
				else
				{
					super.setY(super.getY()+scendi);
				}
			}
		

			


	}



	public ArrayList<SferaEnergiaDrago> getEnergia() {
		return energia;
	}



	private Thread o;
	private FinestraGioco p;
	private int velocita;
	private int k;
	private boolean flag;
	private ArrayList<SferaEnergiaDrago> energia;
	private int frequenzaSparo;
	private boolean variabile;
	private long startTime;
}
