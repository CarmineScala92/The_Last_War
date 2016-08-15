package progettoTpa;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Game extends JFrame {

	
	private JFrame gameWindow;
	private JPanel pannello;
	private JLabel titolo;
    private JButton play;
    private JButton score;
    private JLabel nickname;
	private JTextField nickname1;
	private JLabel copyright;
	private boolean flag;
    private JLabel alert;
  private String level;
	public Game(){
		gameWindow =new JFrame();
		flag=true;
		level="Facile";
		setSize(600,600);
		setTitle("Dragon Ball - The Last War");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false); //non posso ridimensionarla
		creaBottoni();
		creaNickName();
		creaPannello();
		setVisible(true);
	}
	
	public void creaNickName(){
		
		
		this.nickname=new JLabel("NickName ");
		this.nickname1=new JTextField(10);
		this.copyright=new JLabel("Copyright by Scala Carmine, Giuliano Antonio, Genio Francesco ");
		this.copyright.setBackground(Color.red);
		this.alert=new JLabel("");
		alert.setForeground(Color.RED);
	}
	
	public void creaBottoni(){
		
	   play=new JButton();
	   
	   play.setText("Play");
	   score=new JButton();
	   score.setText("Score");
	   
	   
	   //creo classe che gestisce l'evento del click per entrambi i bottoni
	   
	   
	    class GestoreClick implements ActionListener{

	    	
	    	private Game f; 
		   public GestoreClick(Game f){
			   
			   this.f=f;
		   }
	    	
	    	
			public void actionPerformed(ActionEvent e) {
				
				JButton bottone= (JButton)e.getSource();
				
				
				if(bottone.getText()=="Play"){//gestisco quando premo play
					System.out.println(nickname1.getText());
					if(nickname1.getText().equals("")){//controllo se ho scritto qualcosa in nickName
						
						alert.setText("Inserisci Nickname");
						
						
					}
					else//creo la finestra che fa giocare
					{
						setVisible(false);
						
						gameWindow.setTitle("Nome Gioco v. 1.0 - Developed by Scala,Giuliano,Genio");
						gameWindow.add(new FinestraGioco(this.f.level , this.f.nickname1.getText(),f,gameWindow));
						//faro la classe pannello che rappresenta il gioco 
						gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						gameWindow.setResizable(false);						
						gameWindow.setSize(1100,750);
						Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
						gameWindow.setLocation(dim.width/2-gameWindow.getSize().width/2, dim.height/2-gameWindow.getSize().height/2);
						gameWindow.setVisible(true);
						
					}
				}
				else{//gestisco quando viene premuto score
					
					FinestraPunteggio finestra = new FinestraPunteggio(false,"anonimo",0,level,null);
			    	finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    	finestra.setSize(500, 500);
			    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			    	finestra.setLocation(dim.width/2-finestra.getSize().width/2, dim.height/2-finestra.getSize().height/2);
			    	finestra.setVisible(true);
					
				}
			}
	    }
	   
	    
	    
	    //associo i bottoni alla classe che gestisce l'evento
	    GestoreClick g=new GestoreClick(this);
		play.addActionListener(g);
		this.score.addActionListener(g);
	  
	}
	
	public void creaPannello(){//creo il pannello generale che inserisco poi nel frame 
		
		pannello=new JPanel();
		pannello.setLayout(new BorderLayout());
	
		
		
		titolo=new JLabel();
		titolo.setText("Plays to survive the Dragons");
		
		
		
		
		JPanel p1=new JPanel();
		p1.add(titolo);
		pannello.add(p1,BorderLayout.NORTH);
		
		
		
		JLabel sfondo = new JLabel(new ImageIcon("sfondo3.png"));//immagine centrale del gioco
		JPanel p2=new JPanel();
		
		p2.add(sfondo);
		pannello.add(p2,BorderLayout.CENTER);
		
		
		JPanel pannelloSud=new JPanel();
		pannelloSud.setLayout(new BorderLayout());
		
		JPanel pannelloDifficolta=new JPanel();
		pannelloDifficolta.setLayout(new GridLayout(5,2));
		
		JLabel level=new JLabel("Choose Level:");
		
		//mi servono per creare lo spazio
		JPanel p20=new JPanel();
		JPanel p21=new JPanel();
		JPanel p22=new JPanel();
		JPanel p23=new JPanel();
		JPanel p24=new JPanel();
		JPanel p25=new JPanel();
		
		JRadioButton facile   = new JRadioButton("Facile"  , true);//facciamo come selezionato il libello facile
		JRadioButton medio    = new JRadioButton("Medio"   , false);
		JRadioButton difficile = new JRadioButton("Difficile", false);

		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(facile);
		bgroup.add(medio);
		bgroup.add(difficile);
		
		pannelloDifficolta.add(p20);
		pannelloDifficolta.add(level);
		pannelloDifficolta.add(p21);
		pannelloDifficolta.add(facile);
		pannelloDifficolta.add(p22);
		pannelloDifficolta.add(medio);
		pannelloDifficolta.add(p23);
		pannelloDifficolta.add(difficile);
		pannelloDifficolta.add(p24);
		
		JPanel pannelloAvvio=new JPanel();
		pannelloAvvio.setLayout(new GridLayout(2,1));
		
		JPanel pannelloBottoni=new JPanel();
		pannelloBottoni.add(play);
		pannelloBottoni.add(score);
		
		JPanel pannelloNickName=new JPanel();
		pannelloNickName.add(nickname);
		pannelloNickName.add(nickname1);
		
		pannelloAvvio.add(pannelloNickName);
		pannelloAvvio.add(pannelloBottoni);
		
		JPanel p26=new JPanel();
	    p26.add(alert);

		p25.add(copyright);
		p25.setBackground(Color.WHITE);
		pannelloSud.add(p26,BorderLayout.CENTER);
		pannelloSud.add(p25,BorderLayout.SOUTH);
	    pannelloSud.add(pannelloDifficolta,BorderLayout.WEST);
	    pannelloSud.add(pannelloAvvio,BorderLayout.EAST);
		pannello.add(pannelloSud,BorderLayout.SOUTH);
		
		
		
		
		
		class GestoreRadioButton implements ActionListener
		{
			public GestoreRadioButton(Game f)
			{
				this.f=f;
			}

			public void actionPerformed(ActionEvent e) 
			{
				this.f.level=e.getActionCommand();
				
				
			}
			
		Game f;		
		}
		GestoreRadioButton rb1=new GestoreRadioButton(this);
		facile.addActionListener(rb1);
		medio.addActionListener(rb1);
		difficile.addActionListener(rb1);
		add(pannello);
	}
	
	
	
	
	public static void main(String[] args)//da qui creo l'istanza della classe che mi fa partire la finestra (menu per cominciare a giocare)
	{
		Game t=new Game();
	}
	

	

}
