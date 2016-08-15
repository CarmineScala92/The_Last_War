package progettoTpa;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
//gestisco la barra della vita del drago grande
public class BarraEnergia extends GestoreImmagini {

	
	private FinestraGioco p;

	
	public BarraEnergia(FinestraGioco p, int x, int y)
	{
		
		super(new ImageIcon("100.png").getImage(),x,y);		
		this.p=p;
	}
	
	
	
	
	public void paint(Graphics g){
		
		Graphics2D g2=(Graphics2D)g;
		
		g2.drawImage(this.getImage(), this.getX(), this.getY(),p);
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
