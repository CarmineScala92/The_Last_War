package progettoTpa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FinestraPunteggio extends JFrame   {

	private JPanel principale ;
	private JTable punteggio ;
	private DefaultTableModel synsetDtm;
	private boolean state;
	private String nick , level;
	private int score;
private Game game;
	private FinestraGioco gameWindow;
	
	


	public FinestraPunteggio (boolean state2 , String nickname , int score , String level, FinestraGioco gameWindow)
	{
		
		this.setBackground(Color.white);
		this.score=score;		
		this.level=level;
		this.gameWindow=gameWindow;
	
		nick=nickname;
		state=state2;
		try {
			creaPannello();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.add(principale);
		setVisible(true);
	}

	public JPanel creaPannello () throws SQLException
	{
		principale = new JPanel();
		principale.setLayout(new BorderLayout());
		JPanel nord = new JPanel();			
		nord.setSize(50, 100);
		JPanel center = new JPanel();
		JPanel sud = new JPanel();
		punteggio = new JTable();
		punteggio.setEnabled(false);		

		int rigaDBDifficolta = PunteggiDB.contaRigheConLivelloDifficolta(level); 

		if(rigaDBDifficolta < 10 && score != 0 )
		{
			PunteggiDB.inserisciNuovoPunteggio(nick, score, level);
		}
		else {
			int punteggioMinoreDifficolta = PunteggiDB.returnPunteggioMinore(level);
			if(score > punteggioMinoreDifficolta)
			{
				PunteggiDB.deletePunteggio(PunteggiDB.searchIdPunteggioMinore(level));
				PunteggiDB.inserisciNuovoPunteggio(nick, score, level);
			}
		}

		ArrayList<Punteggio> punteggioArray = PunteggiDB.getPunteggiLivello(level);
		synsetDtm = new DefaultTableModel(
				new String[][] {  },	    		
				new String[] { "Nickname", "Score","Level" });

		for (int i = 0; i < punteggioArray.size(); i++) {
			Vector riga = new Vector();			
			Punteggio rigaPunteggio = punteggioArray.get(i);			
			riga.addElement(rigaPunteggio.getNickname());
			riga.addElement(rigaPunteggio.getScore());
			riga.addElement(rigaPunteggio.getLivello());
			synsetDtm.addRow(riga);	
		}


		punteggio.setBackground(Color.cyan);
		punteggio.setModel(synsetDtm);
		//Creato una tabella vuota con 3 colonne
		//Aggiungere una riga alla volta alla colonna. Crea un vettore di 3 elementi e lo aggiungi alla tabella. 
		//Ogni vettore rappresenta una riga.




		if(state==true)
		{

			nord.add(new JLabel ("Congratulations " + nick + " you have won - "));
			nord.add(new JLabel("Level: " + level));

		}
		else 
		{
			
			if(gameWindow==null){
				
				nord.add(new JLabel ("Classifica livello   " + level ));
			}
			else{
			nord.add(new JLabel ("Sorry , " + nick + " You Lose - "));	
			nord.add(new JLabel("Level: " + level));
			}
		}
		creaBottoni();
		sud.add(esci);
		if(gameWindow!=null)
		sud.add(riprova);
		//sud.add(riprova);
		JScrollPane scroll = new JScrollPane(punteggio);  
		principale.add(nord,BorderLayout.NORTH);  
		center.add(scroll);
		principale.add(center, BorderLayout.CENTER);
        principale.add(sud,BorderLayout.SOUTH);
		return null;


	}
	public void creaBottoni(){
        
		esci = new JButton();
		
		if(gameWindow!=null){
			riprova = new JButton();
   		riprova.setText("Try Again");
		
		}
		esci.setText("Exit");

		class GestoreClick implements ActionListener{


			private FinestraPunteggio f; 
			public GestoreClick(FinestraPunteggio f){

				this.f=f;
			}


			public void actionPerformed(ActionEvent e) {
				
				JButton bottone= (JButton)e.getSource();
				
				
				
				if(bottone.getText()=="Exit"){//gestisco quando premo exit
					
					f.setVisible(false);
					if(gameWindow!=null)
					gameWindow.setAviableWindow(false);
				   	}
				}
		}
		GestoreClick g=new GestoreClick(this);
		esci.addActionListener(g);
		
		
		
		
		
		class tornaMenu implements ActionListener{

			private FinestraPunteggio f; 
			public tornaMenu(FinestraPunteggio f){

				this.f=f;
			}
			
			
			public void actionPerformed(ActionEvent arg0) {
				
			f.setVisible(false);
			
			if(gameWindow==null){
				
			}
			else{
				gameWindow.setAviableWindow(false);
				Game t=new Game();
				}
				}
			}
		if(gameWindow!=null)
		riprova.addActionListener(new tornaMenu(this));
		
		
		
	}





	

	private JButton riprova;
	private JButton ritorna;
	private JButton esci;
}
