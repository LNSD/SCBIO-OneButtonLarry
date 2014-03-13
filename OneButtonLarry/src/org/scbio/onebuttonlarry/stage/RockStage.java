package org.scbio.onebuttonlarry.stage;

import org.scbio.onebuttonlarry.R;
import org.scbio.onebuttonlarry.game.GameStage;
import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.Larry;
import org.scbio.onebuttonlarry.game.Sprite;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;

public class RockStage extends GameStage {

	/*
	 * Stage map constants.
	 */
	private static final int BG_RES = R.drawable.stagebg_runstop;
	private static final float LARRY_GROUND_Y = 0.626f;
	private static final float STONE_GROUND_Y = 0.51f;

	private GameView parent;
	private RunLarry larry;
	private Rock stone;

	public RockStage(Context context, GameView parent) {	
		this.parent = parent;

		larry = new RunLarry(context, parent, 0.1f);
		this.stone = new Rock(context, parent);		
		
		restartStage();
		
		this.setStageBackground(BG_RES);
	}
		
	@Override
	public void onDrawStage(Canvas canvas) {
		larry.drawSprite(canvas);
		stone.drawSprite(canvas);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		restartStage();
	}
	
	/*
	 * Called when user touches the GameView
	 */
	@Override
	protected void onTap() {		
		larry.doAction(); // Accelerate Larry
	}
	
	/*
	 * Called by game update thread 
	 */
	
	@Override
	protected void updatePhysics(double delay) {
		larry.updateLarry(delay);
		stone.updateRock();

		
		if(larry.distance(stone) < Rock.KILL_DISTN*parent.getWidth())
		{
			larry.kill();
			restartStage();
		}
		
		larry.incPos(delay);
		stone.incPos(delay);
		
		if(larry.getPosX() > parent.getWidth()-larry.getWidth()/2) finishStage();
	}
	
	@Override
	protected void restartStage() 
	{
		larry.setPos(stone.getWidth() + larry.getWidth(), LARRY_GROUND_Y*parent.getHeight());
		larry.setIncX(RunLarry.LARRY_STARTSPEED*parent.getWidth()); 
		larry.setIncY(0);
		
		stone.setPos(-stone.getWidth()/10, STONE_GROUND_Y*parent.getHeight());
		stone.setIncX(Rock.ROCK_SPEED*parent.getWidth());
		stone.setIncY(0);
	}


	private class RunLarry extends Larry{
		
		/*
		 * Larry constants.
		 */
		public static final float LARRY_STARTSPEED = 0.00234f;
		public static final float LARRY_MAXVEL = 0.0086f;
		public static final float LARRY_ACCEL = 0.0004f;
		private SoundPool mSoundPool;
				
		private int deathSound;
			
		public RunLarry(Context context, View view, float scale) {
			super(context, view, scale);
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);
			deathSound = mSoundPool.load(context, LARRY_SOUND_DIE, 1);
		}
		
		/*
		 * Accelerate Larry
		 */
		@Override
		protected void doAction() { 
			if(this.getIncX() < RunLarry.LARRY_MAXVEL*parent.getWidth()){
				this.setIncX(this.getIncX()+ parent.getWidth()*LARRY_ACCEL);
			}
		}
		
		protected void kill(){
			if(parent.areGameSoundEffectsEnabled()) mSoundPool.play(deathSound, 1, 1, 1, 0, 1);
		}
	}


	public class Rock extends Sprite {
		public static final float ROCK_SPEED = 0.0055f;
		public static final float ROCK_ACCEL = 0.00004f;
		public static final float KILL_DISTN = 0.117f;

		// Resource names
		private static final int STONE_SPRITE = R.drawable.stone;
		
		public Rock(Context context, View view) {
			super(view, context.getResources().getDrawable(STONE_SPRITE));
		}
		public Rock(Context context, View view, float scale) {
			super(view, context.getResources().getDrawable(STONE_SPRITE), scale);
		}
		
		public void updateRock(){
			stone.setIncX(stone.getIncX() + ROCK_ACCEL*parent.getWidth());
		}
		
	}
}



