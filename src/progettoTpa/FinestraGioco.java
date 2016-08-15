package progettoTpa;





import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinestraGioco extends JPanel  implements Runnable,ActionListener {

	
	private ArrayList<Nemico> nemico;//lista di draghi
	private Giocatore player; //goku
    private Thread t;
    private BufferedImage orIm;
    private Image subIm;
    private int k;
    private int durataLevel;
	private int cont;
	private boolean protetto;
	private String labelPunteggio;
	private String labelColpi;
	private String labelImmunita;
	private boolean colpito;
	private NemicoGrande dragoGrande;
	private BonusImmunita immunita;
	private boolean flag;
	private Random random;
	private long startTime;
	private long tempoImmunita;
	private long tempoBonusEnergia;
	private boolean immune;
	private BarraEnergia barraEnergia;
	private int percentuale;
	 private int colpiSubitiDrago;
	 private boolean escoMetodo;
	 private BonusSfereEnergia bonusEnergia;
	 private boolean flag1;
	 private Esplosione esplosione;
	 private boolean colpitoNemico,suono;
	 private Sound so;
	 private String level;
	 private int score;
	 private String nickname;
	 private int c;
	 private boolean nemici;
	 private Game gioco;
	 private JFrame stesso;
	public FinestraGioco(String level , String nick,Game gioco,JFrame stesso){
		nickname=nick;
		this.stesso=stesso;
		this.level=level;
		loadOstacoli();
		c=0;
		this.gioco=gioco;
		flag=false;
		flag1=false;
		immune=false;
		nemici=true;
		suono=true;//mi serve perche senno suona semrpe ..perche paint viene chiamato sempre ,mettendo a false lo rikiama ma non fa piu il suono
		colpitoNemico=false;
		player=new Giocatore(this,"fdfsdf");//creo il giocatore deve essergli passato il nickname che seleziona nella finestra Game quindi finestra giocatore prendera un altro parametro (nickname)
		setFocusable(true);
		addKeyListener(player);
		esplosione=new Esplosione(this,1800,900);
		random=new Random();//lo uso per le cordinate dei bonus
		escoMetodo=true;//quando diventa false vuol dire che muore il drago grande 
		colpiSubitiDrago=0;
	    barraEnergia=new BarraEnergia(this,850,10);//decido dove posizionarla
		this.labelPunteggio="Score : "+player.getPunteggio();
		this.labelColpi="Colpi : "+player.getSfereEnergia();
		tempoBonusEnergia=System.currentTimeMillis();
		startTime = System.currentTimeMillis();
		t=new Thread(this);//è usato per forzare il metodo paint chiamando repaint(ma anche senza paint viene richiamato)
		t.start();
		score = 0;
		}

	//da completare 
    
	
	
	 private int[][] posFacile ={ //creo le cordinate dei 2 nemici livello semplice
			
	        {600,5},{780,500},
	        //{600,390},{660,480},
	       // {730,390},{750,190}
	        };
	 private int[][] posMedio ={ //creo le cordinate dei 2 nemici livello medio
				
		        {600,5},{780,500},
		        {640,200},{670,480},
		       // {730,390},{750,190}
		        };
	 private int[][] posDifficile ={ //creo le cordinate dei 2 nemici livello difficile
				
		        {600,5},{780,500},
		        {690,390},{660,480},
		        {730,290},{750,190}
		        };

	// ----------------------metodi per gestire i label in alto a sinistra (per disegnarli)

	public void paintPunteggio(Graphics2D g2)
	{   
		this.labelPunteggio="Score : "+player.getPunteggio();
		g2.setColor(Color.YELLOW);
		g2.setFont(new Font("Arial", Font.BOLD, 15));
		g2.drawString(this.labelPunteggio,10,20);
	}
	
	public void paintNumColpi(Graphics2D g2)
	{
		g2.setColor(Color.YELLOW);
		g2.setFont(new Font("Arial", Font.BOLD, 15));
		g2.drawString(this.labelColpi,90,20);
	}
	
	public void paintLabelImmunita(Graphics2D g2)
	{
		g2.setColor(Color.RED);
		g2.setFont(new Font("Arial", Font.BOLD, 15));
		g2.drawString(this.labelImmunita,180,20);
	}
	
	public void paintBarraEnergia(Graphics2D g2){//disegno la barra a 100% del drago grande
	
		barraEnergia.paint(g2);
	}
	
	
	//-------------------metodi per disegnare le figure nella finestra di gioco
	public void paint(Graphics g)
	{
		
		
		for(int i=0;i<nemico.size();i++){
			
			if(!nemico.get(i).isVisible()){
				nemici=false;
			}
			else
			{
				nemici=true;
				break;
			}
			
		}
		Graphics2D g2=(Graphics2D)g;
	
	    if(colpito){//quando si perde cioè goku colpito
	    	
	    	scorriImage(g2);
	    	player.paint(g2);
	     
	    	dragoGrande.paint(g2);
	        printOstacoli(g2);
			paintPunteggio(g2);
			paintNumColpi(g2);

			paintBarraEnergia(g2);
			
			
			for(int i=0;i<player.getColpi().size();i++)
			{
				player.getColpi().get(i).die();
			}
			//quando goku muore blocco i thread dei nemici e delle palle di fuoco
			for(int i=0;i<nemico.size();i++){
				
				nemico.get(i).setGestioneThread(false);
				
				for(int j=0;j<nemico.get(i).getEnergia().size();j++){
					
					nemico.get(i).getEnergia().get(j).setGestioneThread(false);
				}
				
			}
			dragoGrande.setVar(false);
			
			for(int i=0;i<dragoGrande.getEnergia().size();i++){
				
				dragoGrande.getEnergia().get(i).setVar(false);
			}
			
			//inserisco l'immagine game over
	    	g2.drawImage(new ImageIcon("game_over.png").getImage(), 200, 200, this);
	    	if(suono){
	    	so=new Sound("end");//suono di game over
	    	suono=false;
	        
	    	FinestraPunteggio finestra = new FinestraPunteggio(false,nickname,player.getPunteggio(),level,this);
	    	finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	finestra.setSize(500, 500);
	    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    	finestra.setLocation(dim.width/2-finestra.getSize().width/2, dim.height/2-finestra.getSize().height/2);
	    	finestra.setVisible(true);
	    	}
	    	
	    }
	    else if(!escoMetodo && !nemici){//quando muoiono tutti i draghi si vince 
	    	scorriImage( g2);
	    	player.paint(g2);
	    	paintPunteggio(g2);
			paintNumColpi(g2);
			//disegnaBonusEnergia(g2);
			paintBarraEnergia(g2);
		
			//quando goku muore blocco i thread dei nemici e delle palle di fuoco
			for(int i=0;i<nemico.size();i++){
				
				nemico.get(i).setGestioneThread(false);
				
				for(int j=0;j<nemico.get(i).getEnergia().size();j++){
					
					nemico.get(i).getEnergia().get(j).setGestioneThread(false);
				}
				
			}
			
	    	g2.drawImage(new ImageIcon("winner.png").getImage(), 200, 200, this);
	    	if(suono){
		    	so=new Sound("RitaglioVittoria");//suono di game over
		    	suono=false;
		    	FinestraPunteggio finestra = new FinestraPunteggio(true,nickname,player.getPunteggio(),level,this);
		    	finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	finestra.setSize(500, 500);
		    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		    	finestra.setLocation(dim.width/2-finestra.getSize().width/2, dim.height/2-finestra.getSize().height/2);
		    	finestra.setVisible(true);
		    	}
	    	player.die();
	    	
	    }
	    else if(immune){//gestisco quando prende la protezione
	    	
	    	scorriImage( g2);
			player.paint(g2);
			dragoGrande.paint(g2);
			collisioneOstacolo(g2);
			collisioneSfere();
			collisioneSfereGokuControSferaGrande();
			paintBarraEnergia(g2);
						
			if(colpitoNemico){
				esplosione.paint(g2);	
				colpitoNemico=false;
				
			}
			if(escoMetodo)//controllo la collisione fin quando il drago è vivo se muore che la controllo a fare
				collisioneDragoGrandeconSferaGoku(g2);
			
			if(!flag1){
				  
				gestioneBonusEnergia();
				}
				else{
					disegnaBonusEnergia(g2);
					intersezioneGokuBonusEnergia();
					tempoBonusEnergia = System.currentTimeMillis();
				}
			//controlImmunita();
			tempoImmunita();
			paintLabelImmunita(g2);
			printOstacoli(g2);
			paintPunteggio(g2);
			paintNumColpi(g2);
			startTime = System.currentTimeMillis();
			this.labelColpi="Colpi : "+player.getSfereEnergia();
	    }
	    else{//quando non ha nessun bonus
		
		scorriImage( g2);
		printOstacoli(g2);
		player.paint(g2);
		dragoGrande.paint(g2);
		collisioneOstacolo(g2);
		collisioneGiocatore();
		collisioneGokuSferaDragoGrande();
		collisioneSfereGokuControSferaGrande();
		collisioneSfere();
		paintBarraEnergia(g2);		
		if(!flag){
	  
		gestioneImmunita();
		}
		else{
			disegnaImmunita(g2);
			controlImmunita();
			tempoImmunita = System.currentTimeMillis();
			startTime = System.currentTimeMillis();
		}
		
		
		if(!flag1){
			  
			gestioneBonusEnergia();
			}
			else{
				disegnaBonusEnergia(g2);
				intersezioneGokuBonusEnergia();
				tempoBonusEnergia = System.currentTimeMillis();
			}
		
		if(escoMetodo)//controllo la collisione fin quando il drago è vivo se muore che la controllo a fare
		collisioneDragoGrandeconSferaGoku(g2);
		
		if(colpitoNemico){
			esplosione.paint(g2);	
			colpitoNemico=false;
		}
		
		
		
		paintPunteggio(g2);
		paintNumColpi(g2);
		
		this.labelColpi="Colpi : "+player.getSfereEnergia();
	    }
		Toolkit.getDefaultToolkit().sync();
		g2.dispose();	
	}
	
	
	public void scorriImage(Graphics2D g2)
	{
		
			// g2.drawImage(new ImageIcon("grande_collisione.jpg").getImage(), 0, 0, this);
			try {
				orIm=ImageIO.read(new File("universo.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			subIm=orIm.getSubimage(k, 0, 1100, 800);
			g2.drawImage(subIm,0,0,this);
		
		
	}

	//-------------------------metodi dove gestisco le varie collisioni------------------------------------------------------------
	
	//quando la sfera blu colpisce il drago
	public void collisioneOstacolo(Graphics2D g2)
	{
		for(int i=0;i<this.player.getColpi().size();i++)//scorro la lista dei colpi
		{
			for(int j=0;j<this.nemico.size();j++)//scorro la lista dei nemici
			{
				if( this.player.getFired() && this.player.getColpi().get(i).getBounds().intersects(this.nemico.get(j).getBounds()))
				 {
					
					esplosione.setImage(new ImageIcon("esplosioneDrago.png").getImage());
						esplosione.setX(player.getColpi().get(i).getX());
						esplosione.setY(player.getColpi().get(i).getY());
						this.player.getColpi().get(i).die();
						
						
						
						//this.nemico.remove(j);
					colpitoNemico=true;
					
					if(nemico.get(j).isVisible()){
					player.addPunteggio(level,0);
					paintPunteggio(g2);
					}
					this.nemico.get(j).die();
					
				 }
			}
		}
	}
	
	
	//quando goku viene colpito da una sfera rossa
	public void collisioneGiocatore()
	{
		boolean var=false;
		for(int i=0;i<this.nemico.size();i++)//scorro la lista dei nemici
		{
			for(int j=0;j<this.nemico.get(i).getEnergia().size();j++)//scorro la lista dei nemici
			{
				if( this.player.getBounds().intersects(this.nemico.get(i).getEnergia().get(j).getBounds()))
				 {
						//esplosione.setX(player.getColpi().get(i).getX());
						//esplosione.setY(player.getColpi().get(i).getY());			
					
					   this.nemico.get(i).getEnergia().get(j).die();
					   this.player.die();
					   this.colpito=true;//variabile che mi fa capire che goku è morto
					   var=true;//mi serve per rendere il ciclo piu efficiente
					   this.t.interrupt();
					   break;
					
				 }
			}
			if(var)
				break;
		}
	}
	
	
	
	//collisione sferaAzzurra e sferaRossa
	
	
	public void collisioneSfere(){
		
		for(int i=0;i<player.getColpi().size();i++){
			
			 int numNemici=nemico.size();
			
		   for(int k=0;k<numNemici;k++){
			 
			   for(int j=0;j<nemico.get(k).getEnergia().size();j++){
				
				   if(player.getColpi().get(i).getBounds().intersects(nemico.get(k).getEnergia().get(j).getBounds())){
					
					   esplosione.setImage(new ImageIcon("collisioneSfere.png").getImage());
					   esplosione.setX(player.getColpi().get(i).getX());
						esplosione.setY(player.getColpi().get(i).getY());	
						
					  player.getColpi().get(i).die();
					  
					  nemico.get(k).getEnergia().get(j).die();
					  colpitoNemico=true;
					
				     }
				
			     }
			 }
		}
	}
	
	
	public void collisioneGokuSferaDragoGrande(){
		
		
		for(int j=0;j<this.dragoGrande.getEnergia().size();j++)//scorro la lista delle sfere lanciate dal drago grande
		{
			if( this.player.getBounds().intersects(this.dragoGrande.getEnergia().get(j).getBounds()))
			 {
					//boom.setX(player.getColpi().get(i).getX());
					//boom.setY(player.getColpi().get(i).getY());			
				
				   this.dragoGrande.getEnergia().get(j).die();
				   this.player.die();
				   this.colpito=true;//variabile che mi fa capire che goku è morto
				   break;
			 }
		}
		
		
		
	} 
	
	
public void collisioneSfereGokuControSferaGrande(){
		int j;
		for(int i=0;i<player.getColpi().size();i++){//lista sfere lanciate da goku
			
			for( j=0;j<this.dragoGrande.getEnergia().size();j++)//scorro la lista delle sfere lanciate dal drago grande
			{
				
				   if(player.getColpi().get(i).getBounds().intersects(dragoGrande.getEnergia().get(j).getBounds())){
					
					  	
					   
					  player.getColpi().get(i).die();//faccio scomparire solo la sfera di goku
					 
					  }
				
			   }
			 }
           }
	
//nel livello semplice e medio il drago grande inizia a perdere vita ogn ivolta che viene toccato ,invece nel difficile devono essere uccisi prima i draghi neri
public void collisioneDragoGrandeconSferaGoku(Graphics2D g2){
	if(level=="Difficile" && !nemici){
			for(int i=0;i<player.getColpi().size();i++){//mi scorro i colpi di goku
				if(player.getColpi().get(i).getBounds().intersects(dragoGrande.getBounds())){
					colpiSubitiDrago++;
					player.getColpi().get(i).die();
				}
			}
			
			if(colpiSubitiDrago==1 && c==0){
				
				player.addPunteggio(level,1);
				paintPunteggio(g2);	
				barraEnergia.setImage(new ImageIcon("75.png").getImage());
				c++;
			}
		    if(colpiSubitiDrago==2 && c==1){
		    	
		    	    player.addPunteggio(level,1);
					paintPunteggio(g2);	
					
		    	barraEnergia.setImage(new ImageIcon("50.png").getImage());
		    	c++;
			}
		    if(colpiSubitiDrago==3 && c==2){
		    	player.addPunteggio(level,1);
		    	paintPunteggio(g2);	
		    	barraEnergia.setImage(new ImageIcon("25.png").getImage());
		    	c++;
		    }
		    if(colpiSubitiDrago>=4 && c==3){
		    	player.addPunteggio(level,1);
		    	paintPunteggio(g2);	
		    	long tempoUltimoColpo = System.currentTimeMillis();
		       	barraEnergia.setImage(new ImageIcon("0.png").getImage());
		       	esplosione.setX(this.dragoGrande.getX());
				esplosione.setY(this.dragoGrande.getY());
				esplosione.setImage(new ImageIcon("esplosioneDragoMaggiore.png").getImage());
				colpitoNemico=true;	
		    	dragoGrande.die();
		    	escoMetodo=false;
		    	c++;
		    }
		    paintPunteggio(g2);	 	
	}else if(level=="Facile" || level=="Medio"){
		for(int i=0;i<player.getColpi().size();i++){//mi scorro i colpi di goku
			if(player.getColpi().get(i).getBounds().intersects(dragoGrande.getBounds())){	
				colpiSubitiDrago++;
				player.getColpi().get(i).die();
			}
		}
		
		if(colpiSubitiDrago==1 && c==0){
			
			player.addPunteggio(level,1);
			paintPunteggio(g2);	
			barraEnergia.setImage(new ImageIcon("75.png").getImage());
			c++;
		}
	    if(colpiSubitiDrago==2 && c==1){
	    	
	    	    player.addPunteggio(level,1);
				paintPunteggio(g2);	
				
	    	barraEnergia.setImage(new ImageIcon("50.png").getImage());
	    	c++;
		}
	    if(colpiSubitiDrago==3 && c==2){
	    	player.addPunteggio(level,1);
	    	paintPunteggio(g2);	
	    	barraEnergia.setImage(new ImageIcon("25.png").getImage());
	    	c++;
	    }
	    if(colpiSubitiDrago>=4 && c==3){
	    	player.addPunteggio(level,1);
	    	paintPunteggio(g2);	
	    	long tempoUltimoColpo = System.currentTimeMillis();
	       	barraEnergia.setImage(new ImageIcon("0.png").getImage());
	       	esplosione.setX(this.dragoGrande.getX());
			esplosione.setY(this.dragoGrande.getY());
			esplosione.setImage(new ImageIcon("esplosioneDragoMaggiore.png").getImage());
			colpitoNemico=true;	
	    	dragoGrande.die();
	    	escoMetodo=false;
	    	c++;
	    }
	}
}
	
	
	
	
	//-----------------------metodi per gestire il bonus immunitaà------------------------------
	
	//non avremo mai due immunita insieme nella mappa 
	public void gestioneImmunita(){
		
		long seconds = (System.currentTimeMillis() - startTime) / 1000;
		
		if(seconds==7)//ogni sette secondi esce un bonus immunita
		{   flag=true;
		
		    int x = 150+random.nextInt(300);//le creo in coordinate casuali 
		    int y = random.nextInt(300);
			immunita=new BonusImmunita(this, x,  y,10);
			
		}
		
		
	}
	
	//chiamata per disegnare il bonusImmunita
	public void disegnaImmunita(Graphics g){
		
		if(immunita.isVisible())
		immunita.paint(g);
		else
			flag=false;
		

		
	}
	
	
	//mi cambia l'immagine da goku a goku con la protezione
	public void controlImmunita()
	{
	 
		if(this.player.getBounds().intersects(this.immunita.getBounds()))
		{
			player.setImage(new ImageIcon("gokuScudo.png").getImage());
			immune=true;
		
			this.immunita.die();
			tempoImmunita = System.currentTimeMillis();
		}
	}
	
	public void tempoImmunita(){//faccio durare l'immunita 5 secondi per facile 7 medio 
		int tempo=0;
		long seconds = (System.currentTimeMillis() - tempoImmunita) / 1000;
		
		this.labelImmunita="Immunita per : "+Math.abs(seconds-5);
		if(level=="Facile")
			tempo=5;
		
		if(level=="Medio")
			tempo=6;
		
		if(level=="Difficile")
		   tempo=5;
		
		if(seconds==tempo)
		{   
			player.setImage(new ImageIcon("player2.png").getImage());
			immune=false;
			flag=false;
		}
		
	}
	
	
	
	
	//----------------------------gestione Bonus Energia(cioe colpi goku)
	
	
	
public void disegnaBonusEnergia(Graphics g){//questo metodo mi serve per per disegnare il bonus
		
		if(bonusEnergia.isVisible())
		bonusEnergia.paint(g);
		else
			flag1=false;
	 }


	public void gestioneBonusEnergia(){//decido quando farlo comparire (in questo caso ogni 4 secondi)
		
		long seconds = (System.currentTimeMillis() - tempoBonusEnergia) / 1000;

		if(seconds==4)
		{   
			 flag1=true;
				
			    int x = 100+random.nextInt(350);//le creo in coordinate casuali 
			    int y = random.nextInt(400);
				bonusEnergia=new BonusSfereEnergia(this, x,  y,10);
			seconds=0;
		}
		
		
		
	}
	
	public void intersezioneGokuBonusEnergia(){//controllo quando avviene l'intersezione
		
		if(this.player.getBounds().intersects(this.bonusEnergia.getBounds()))
		{
			
			
			this.bonusEnergia.die();
			System.out.println("ci sn");
			player.setSfereEnergia(player.getSfereEnergia()+6);
			tempoBonusEnergia = System.currentTimeMillis();
			flag1=false;
		}
		
		
		
	}
	
	
	
	public void run() 
	{
		try 
		{
			while(true)
			{
				
				this.repaint();
				//this.movie();
				Thread.sleep(5);
			} 
		}
		catch (InterruptedException e) 
		{
			//e.printStackTrace();
		
			System.out.println("stoppo il principale");
			
			return;
		}
	}

	
	
	
	
	//creo gli ostacoli inviando le cordiante scelte nella matrice
	public void loadOstacoli()
	{
		
		this.nemico=new ArrayList<Nemico>();
		if(level=="Facile"){
			for(int i=0;i<posFacile.length;i++){
			
		
				this.nemico.add(new Nemico(this,posFacile[i][0],posFacile[i][1],4,i+1));
			}
			dragoGrande=new NemicoGrande(this,980,400,6,2);
		}
		
		if(level=="Medio"){
			for(int i=0;i<posMedio.length;i++){
			
		
				this.nemico.add(new Nemico(this,posMedio[i][0],posMedio[i][1],4,i+1));
			}
			dragoGrande=new NemicoGrande(this,980,400,6,1);
		}
		
		if(level=="Difficile"){
			
			for(int i=0;i<posDifficile.length;i++){
			
		
				this.nemico.add(new Nemico(this,posDifficile[i][0],posDifficile[i][1],4,i+1));
			
			}
			dragoGrande=new NemicoGrande(this,980,400,6,1);
		}
		
		
		
		
	}
	
	public void printOstacoli(Graphics g)
	{
		for (int i=0;i<nemico.size();i++)
		{
			nemico.get(i).paint(g);
		}
	}
	
	
	public void setAviableWindow(boolean value)
	{
		stesso.setVisible(value);	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
