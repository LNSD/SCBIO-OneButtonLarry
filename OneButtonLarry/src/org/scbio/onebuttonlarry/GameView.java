package org.scbio.onebuttonlarry;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
	
	private Graphic larry;
	private Activity parent;
	
	public GameView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		Drawable drawableLarry;
		drawableLarry = context.getResources().getDrawable(R.drawable.larry1);
		
		larry = new Graphic(this, drawableLarry);
	}
	
	@Override
	protected void onSizeChanged(int width, int height, int old_width, int old_height) {
		super.onSizeChanged(width, height, old_width, old_width);
		
		larry.setPosX(0*(width-larry.getWidht()));
		larry.setPosY(0.65* (height-larry.getHeight()));		
		}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		larry.dibujaGrafico(canvas);
	}
	
	public void setPadre(Activity parent) {
		this.parent = parent;
		
	}
		
}
	
	


	
