package org.scbio.onebuttonlarry.game;

import org.scbio.onebuttonlarry.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

@SuppressWarnings("unused")
public abstract class Larry extends Sprite {
	
	// Resource names
	private static final int LARRY_FRAME_1 = R.drawable.larry1;
	private static final int LARRY_FRAME_2 = R.drawable.larry2;
	private static final int LARRY_FRAME_3 = R.drawable.larry3;
	private static final int LARRY_FRAME_4 = R.drawable.larry4;
	// Frame drawable
	private static Drawable larryFrame1;
	private static Drawable larryFrame2;
	private static Drawable larryFrame3;
	private static Drawable larryFrame4;
	
	public Larry(Context context, View view, float scale) {
		super(view, context.getResources().getDrawable(LARRY_FRAME_1), scale);
		
		larryFrame1 = context.getResources().getDrawable(LARRY_FRAME_1);
		larryFrame2 = context.getResources().getDrawable(LARRY_FRAME_2);
		larryFrame3 = context.getResources().getDrawable(LARRY_FRAME_3);
		larryFrame4 = context.getResources().getDrawable(LARRY_FRAME_4);
		
		this.setDrawable(larryFrame1);
	}
	
	public Larry(Context context, View view){	
		super(view, context.getResources().getDrawable(LARRY_FRAME_1));
		
		larryFrame1 = context.getResources().getDrawable(LARRY_FRAME_1);
		larryFrame2 = context.getResources().getDrawable(LARRY_FRAME_2);
		larryFrame3 = context.getResources().getDrawable(LARRY_FRAME_3);
		larryFrame4 = context.getResources().getDrawable(LARRY_FRAME_4);
		
		this.setDrawable(larryFrame1);
	}
	
	protected abstract void doAction();
}
