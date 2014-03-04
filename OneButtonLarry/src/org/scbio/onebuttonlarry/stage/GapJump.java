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
	private static final float Y_GROUND = 0.626f;

	private Context context;
	private GameView parent;

	private JumpLarry larry;

	public GapJump(Context context, GameView parent) {	
		this.context = context;
		this.parent = parent;

		larry = new JumpLarry(context, parent, 0.1f);
		
		larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
		larry.setIncX(JumpLarry.LARRY_REGSPEED);
		larry.setIncY(0);
		
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
	
	/*
	 * Called when user touches the GameView
	 */
	@Override
	protected void onTap() {		
		if(!larry.isJumping()) larry.doAction();
	}
	
	/*
	 * Called by game update thread 
	 */
	@Override
	protected void updatePhysics(double delay) {
		larry.updateLarry(delay);
		
		if(larry.isJumping()){ 
			larry.setIncY(larry.jump(delay, larry.getIncY()));
			
			if(larry.getIncY() >= -(5+JumpLarry.LARRY_JUMPSPEEDY))
			{
				larry.jump = false;
				larry.setIncY(0);
				larry.setIncX(JumpLarry.LARRY_REGSPEED);
				larry.setPosY(parent.getHeight()*Y_GROUND-1);
			}
		}
		
		if(!larry.isJumping() && larry.hasFallen()){
			larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
		}
		
		larry.incPos(delay);
		if(larry.getPosX() > parent.getWidth()-larry.getWidth()/2) finishStage();
	}
	
	private class JumpLarry extends Larry{
		public JumpLarry(Context context, View view) {
			super(context, view);
		}
		
		public boolean hasFallen() {
			boolean firstgap = getPosX() > 0.5*parent.getWidth();
			boolean secondgap = false; 
			boolean thirdgap = false;
			return firstgap || secondgap || thirdgap;
		}
		
		public JumpLarry(Context context, View view, float scale) {
			super(context, view, scale);
		}
		
		/*
		 * Larry constants.
		 */
		public static final int LARRY_REGSPEED = 7;
		public static final int LARRY_JUMPSPEEDX = 15;
		public static final int LARRY_JUMPSPEEDY = -45;
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
			return vinit + GRAVITY*t;
		}
	}
}
