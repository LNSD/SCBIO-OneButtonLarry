package org.scbio.onebuttonlarry;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.view.View;

public class Sprite {
	
	private Drawable drawable; //Drawn image
	private double posX, posY; //Position
	private double incX, incY; //Speed
	private int width, height; //Image dimensions
	
	//Donde dibujamos el grafico (usada en view.ivalidate)
	private View view;
	
	//Para determinar el espacio a borrar (view.ivalidate)
	public static final int MAX_SPEED = 20;
	
	public Sprite (View view, Drawable drawable){
		this.view = view;
		this.drawable = drawable;
		
		this.width = drawable.getIntrinsicWidth();
		this.height = drawable.getIntrinsicHeight();
	}
	
	public Sprite (View view, Drawable drawable, float scale){
		this.view = view;
		
		this.width = Math.round(drawable.getIntrinsicWidth()*scale);
		this.height = Math.round(drawable.getIntrinsicHeight()*scale);
		this.drawable = new ScaleDrawable(drawable, 0, width, height).getDrawable();
	}
	
	public void drawSprite(Canvas canvas){
		canvas.save();
		
		int x = (int) (posX + width/2);
		int y = (int) (posY + height/2);
		
		drawable.setBounds((int)posX, (int)posY, (int)posX+width, (int)posY+height);
		
		drawable.draw(canvas);
		canvas.restore();
		
		int rInval = (int) Math.hypot(width, height)/2 + MAX_SPEED;
		view.invalidate(x-rInval, y-rInval, x+rInval, y+rInval);
	}
	
	public void incPos(double factor){
		posX += incX * factor;
		
		if(posX<-width/2) {
			posX = view.getWidth() - width/2;
		}
		if(posX>view.getWidth()-width/2) {posX=-width/2;}
		
		posY+=incY * factor;
		
		if(posY<-height/2) {posY=view.getHeight() -height/2;}
		if(posY>view.getHeight()-height/2) {posY=-height/2;}
	}
	
	public double distance (Sprite g) {
		return Math.hypot(posX - g.posX, posY - g.posY);
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	
	public void setPos(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
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

	public static int getMaxSpeed() {
		return MAX_SPEED;
	}
	
}
