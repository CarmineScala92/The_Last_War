package progettoTpa;

import java.awt.Image;
import java.awt.Rectangle;

//superclasse di tutte quelle classi che rappresentano un immagine (goku,drago,bonus ecc)
public abstract class GestoreImmagini {

	
	private boolean visible;
	private Image image;//immagine
	
	private int x; //posizione asse x e y dell immagine 
	private int y;
	
	private int dx;//utile x gli spostamenti
	private int dy;
	
	
	private int height; // larghezza e altezza img 
	private int width;
	
	
	
	public GestoreImmagini()
	{
		visible=true;
	}
	
	public GestoreImmagini(Image img, int x, int y)
	{
		this.image=img;
		this.x=x;
		this.y=y;
		this.visible=true;
		
		//prelevo larghezza e altezza dell'immagine
		this.width=this.image.getWidth(null);
	    this.height=this.image.getHeight(null);
		
	}
	
	
	//metodi per settare e stampare le varie variabili di istanza
	public void die()
	{
		visible=false;//quando l'immagine deve scomparire
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setVisible(boolean visible)
	{
		this.visible=visible;
	}
	
	
	
	
	public void setImage(Image image)
	{
		this.image=image;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setX(int x)
	{
		this.x=x;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setY(int y)
	{
		this.y=y;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setDx(int dx)
	{
		this.dx=dx;
	}
	
	public int getDx()
	{
		return dx;
	}
	
	public void setDy(int dy)
	{
		this.dy=dy;
	}
	
	public int getDy()
	{
		return dy;
	}
	
	
	public abstract void move();//lo implementeranno tutte le sottoclassi

	public Rectangle getBounds()//posizione del rettangolo nel gioco
	{
		return new Rectangle(this.x,this.y,this.width,this.height);
	}
	
}
