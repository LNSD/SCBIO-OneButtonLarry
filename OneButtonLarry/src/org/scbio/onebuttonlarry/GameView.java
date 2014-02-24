package org.scbio.onebuttonlarry;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {


	private Activity parent;
	private Sprite larry;
	private ArrayList<Sprite> bridge = new ArrayList<Sprite>();


	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);		

		larry = new Sprite(this, context.getResources().getDrawable(R.drawable.larry1), 0.1f);
		for (int i = 0; i < 20; i++) {
			Sprite bridgeTile = new Sprite(this, context.getResources().getDrawable(R.drawable.larry2), 0.1f);
			bridgeTile.setPos(0, 0);
			bridge.add(bridgeTile);
		}
	}

	@Override
	protected void onSizeChanged(int width, int height, int old_width, int old_height) {
		super.onSizeChanged(width, height, old_width, old_width);

		larry.setPos(0*(width-larry.getWidth()), 0.652*(height-larry.getHeight()));		
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);

		larry.drawSprite(canvas);
	}

	public void setParent(Activity parent) {
		this.parent = parent;

	}

}





