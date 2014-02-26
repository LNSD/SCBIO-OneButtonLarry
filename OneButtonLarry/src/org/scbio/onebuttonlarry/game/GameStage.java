package org.scbio.onebuttonlarry.game;

import java.util.ArrayList;

import org.scbio.onebuttonlarry.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class GameStage extends View {

	private Activity parent;
	private Sprite larry; //TODO Delete when refactored


	public GameStage(Context context, AttributeSet attrs) {
		super(context, attrs);		

		larry = new Sprite(this, context.getResources().getDrawable(R.drawable.larry1), 0.1f);
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
