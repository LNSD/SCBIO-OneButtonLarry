package org.scbio.onebuttonlarry.stage;

import org.scbio.onebuttonlarry.R;
import org.scbio.onebuttonlarry.game.GameStage;
import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.Larry;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;

public class GapJump extends GameStage {

	/*
	 * Stage map constants.
	 */
	private static final int BG_RES = R.drawable.stagebg_gapjump;
	private static final float Y_GROUND = 0.626f;
	
	private GameView parent;
	private JumpLarry larry;

	public GapJump(Context context, GameView parent) {	
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
		
		if(larry.hasFallen()){
			larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
		}
		
		larry.incPos(delay);
		if(larry.getPosX() > parent.getWidth()-larry.getWidth()/2) finishStage();
	}
	
	private class JumpLarry extends Larry{
		
		/*
		 * Larry constants.
		 */
		public static final int LARRY_REGSPEED = 6;
		public static final int LARRY_JUMPSPEEDY = -45;
		public static final float LARRY_JUMPSPEEDX = 0.01171875f;
		public static final float GRAVITY = 5f;
		
		private SoundPool mSoundPool;
		private int jumpSound;
		private int deathSound;
		
		private boolean jump = false;
		
		public JumpLarry(Context context, View view) {
			super(context, view);
			
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);
			jumpSound = mSoundPool.load(context, LARRY_SOUND_JUMP, 1);
			deathSound = mSoundPool.load(context, LARRY_SOUND_DIE, 1);
		}		
		public JumpLarry(Context context, View view, float scale) {
			super(context, view, scale);
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);
			jumpSound = mSoundPool.load(context, LARRY_SOUND_JUMP, 1);
			deathSound = mSoundPool.load(context, LARRY_SOUND_DIE, 1);
		}
		
		@Override
		protected void doAction() {
			jump = true;
			if(parent.areGameSoundEffectsEnabled()) mSoundPool.play(jumpSound, 1, 1, 1, 0, 1);
			setIncY(LARRY_JUMPSPEEDY);
			setIncX(LARRY_JUMPSPEEDX*parent.getWidth());
		}
		
		public double jump(double t, double vinit) {
			return vinit + GRAVITY*t;
		}
		
		public boolean isJumping(){
			return jump;
		}
		
		public boolean hasFallen()
		{
			boolean firstgap = (getPosX() > 0.11*parent.getWidth() && getPosX() < 0.18*parent.getWidth());
			boolean secondgap = (getPosX() > 0.26*parent.getWidth() && getPosX() < 0.43*parent.getWidth()); 
			boolean thirdgap = (getPosX() > 0.52*parent.getWidth() && getPosX() < 0.68*parent.getWidth());
			boolean fourthgap = (getPosX() > 0.76*parent.getWidth() && getPosX() < 0.81*parent.getWidth());
			boolean result = (firstgap || secondgap || thirdgap || fourthgap) && !jump;
			
			if(result && parent.areGameSoundEffectsEnabled()) mSoundPool.play(deathSound, 1, 1, 1, 0, 1);
			return result;
		}
	}
}
