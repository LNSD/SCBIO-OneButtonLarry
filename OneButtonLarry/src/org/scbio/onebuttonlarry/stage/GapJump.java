package org.scbio.onebuttonlarry.stage;

import org.scbio.onebuttonlarry.R;
import org.scbio.onebuttonlarry.game.GameStage;
import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.Larry;

import android.content.Context;
import android.graphics.Canvas;

public class GapJump extends GameStage {

	/*
	 * Stage map constants.
	 */
	private static final float Y_GROUND = 0.612f;

	private Context context;
	private GameView parent;

	private Larry jumpLarry;

	public GapJump(Context context, GameView parent) {
		super(parent);	
		this.context = context;
		this.parent = parent;

		jumpLarry = new Larry(context, parent, 0.1f) {

			@Override
			protected void doAction() {
				// TODO Auto-generated method stub

			}
		};
		
		jumpLarry.setIncY(0);
		jumpLarry.setIncX(5);

		this.setStageBackground(R.drawable.stagebg_gapjump);
	}

	@Override
	public void onDrawStage(Canvas canvas) {
		jumpLarry.drawSprite(canvas);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
	
		jumpLarry.setPosY(Y_GROUND*h);
	}

	@Override
	protected void onTap() {
		taps++;
	}

	@Override
	protected void updatePhysics(double delay) {
		jumpLarry.incPos(delay);

		if(jumpLarry.getPosX() > parent.getWidth()-jumpLarry.getWidth()/2) finishStage();
	}
}
