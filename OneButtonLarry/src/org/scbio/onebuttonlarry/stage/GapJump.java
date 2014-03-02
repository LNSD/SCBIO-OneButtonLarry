package org.scbio.onebuttonlarry.stage;

import org.scbio.onebuttonlarry.R;
import org.scbio.onebuttonlarry.game.GameStage;
import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.Larry;

import android.content.Context;

public class GapJump extends GameStage {
	
	/*
	 * Stage map constants.
	 */
	private Context context;
	private GameView parent;
	
	private Larry jumpLarry;
	
	public GapJump(Context context, GameView parent) {
		super(parent);	
		this.context = context;
		this.parent = parent;
		
		jumpLarry = new Larry(context, parent) {
			
			@Override
			protected void doAction() {
				// TODO Auto-generated method stub
				
			}
		};
		
		this.setStageBackground(R.drawable.stagebg_gapjump);
	}

	@Override
	protected void onTap() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updatePhysics(double delay) {
		// TODO Auto-generated method stub

	}

}
