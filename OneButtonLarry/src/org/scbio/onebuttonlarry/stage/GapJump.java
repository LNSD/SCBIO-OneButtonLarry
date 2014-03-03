package org.scbio.onebuttonlarry.stage;

import org.scbio.onebuttonlarry.R;
import org.scbio.onebuttonlarry.game.GameStage;
import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.Larry;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GapJump extends GameStage {

	/*
	 * Stage map constants.
	 */
	private static final int BG_RES = R.drawable.stagebg_gapjump;
	private static final float Y_GROUND = 0.612f;
	
	private Context context;
	private GameView parent;

	private JumpLarry larry;

	public GapJump(Context context, GameView parent) {	
		this.context = context;
		this.parent = parent;

		larry = new JumpLarry(context, parent, 0.1f);
		
		larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
		larry.setIncY(0);
		larry.setIncX(larry.LARRY_REGSPEED);
		
		this.setStageBackground(BG_RES);
	}
		
	@Override
	public void onDrawStage(Canvas canvas) {
		larry.drawSprite(canvas);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
	}

	@Override
	protected void onTap() {
		taps++;
		
		if(!larry.isJumping()) larry.doAction();
	}

	@Override
	protected void updatePhysics(double delay) {
		if(larry.isJumping()) 
			larry.setIncY(larry.jump(delay, larry.getIncY()));
		
		larry.incPos(delay);
		if(larry.getPosX() > parent.getWidth()-larry.getWidth()/2) finishStage();
	}
	
	private class JumpLarry extends Larry{
		public JumpLarry(Context context, View view) {
			super(context, view);
		}
		public JumpLarry(Context context, View view, float scale) {
			super(context, view, scale);
		}
		
		/*
		 * Larry constants.
		 */
		public static final int LARRY_REGSPEED = 7;
		public static final int LARRY_JUMPSPEEDX = 15;
		public static final int LARRY_JUMPSPEEDY = -40;
		public static final float GRAVITY = 5f;
		
		private boolean jump = false;
		
		public boolean isJumping(){
			return jump;
		}
		
		/*
		 * Larry jump action 
		 * @see org.scbio.onebuttonlarry.game.Larry#doAction()
		 */
		@Override
		protected void doAction() {
			jump = true;
			setIncY(LARRY_JUMPSPEEDY);
			setIncX(LARRY_JUMPSPEEDX);
		}
		
		public double jump(double t, double vinit)
		{	
			if(parent.getHeight()*Y_GROUND+5 < getPosY()){
				jump = false;
				setIncX(LARRY_REGSPEED);
				setPosY(parent.getHeight()*Y_GROUND+5);
				return 0;
			}
			
			return vinit + GRAVITY*t;
		}
	}
}
