package org.scbio.onebuttonlarry;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Graphic {
	
	private Drawable drawable; //Imagen que dibujaremos
	private double posX, posY; //Posicion
	private double incX, incY; //Velocidad desplazamiento
	private int widht, height; //Dimensiones imagen
	
	  //Donde dibujamos el grafico (usada en view.ivalidate)
	private View view;
	
	  //Para determinar el espacio a borrar (view.ivalidate)
	public static final int MAX_VELOCIDAD = 20;
	
	public Graphic (View view, Drawable drawable){
		this.view = view;
		this.drawable = drawable;
		widht = drawable.getIntrinsicWidth();
		height = drawable.getIntrinsicHeight();
	}
	
	public void dibujaGrafico(Canvas canvas){
		canvas.save();
		int x=(int) (posX+widht/2);
		int y=(int) (posY+height/2);
		drawable.setBounds((int)posX, (int)posY, (int)posX+widht, (int)posY+height);
		drawable.draw(canvas);
		canvas.restore();
		int rInval = (int) Math.hypot(widht, height)/2 + MAX_VELOCIDAD;
		view.invalidate(x-rInval, y-rInval, x+rInval, y+rInval);
	}
	
	public void incrementaPos(double factor){
		posX+=incX * factor;
		//Si salimos de la pantalla, corregimos posici�n
		if(posX<-widht/2) {posX=view.getWidth() -widht/2;}
		if(posX>view.getWidth()-widht/2) {posX=-widht/2;}
		posY+=incY * factor;
		if(posY<-height/2) {posY=view.getHeight() -height/2;}
		if(posY>view.getHeight()-height/2) {posY=-height/2;}
	}
	
	public double distance (Graphic g) {
		return Math.hypot(posX-g.posX, posY-g.posY);
		
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getIncX() {
		return incX;
	}

	public void setIncX(double incX) {
		this.incX = incX;
	}

	public double getIncY() {
		return incY;
	}

	public void setIncY(double incY) {
		this.incY = incY;
	}

	public int getWidht() {
		return widht;
	}

	public void setWidht(int widht) {
		this.widht = widht;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getMaxVelocidad() {
		return MAX_VELOCIDAD;
	}
	
}
