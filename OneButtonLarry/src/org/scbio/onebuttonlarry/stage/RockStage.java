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
	private static final float Y_GROUND = 0.626f;
	private static final float S_GROUND = 0.51f;

	
	private GameView parent;
	private RunLarry larry;
	private Stone stone;
	

	public RockStage(Context context, GameView parent) {	
		this.parent = parent;

		larry = new RunLarry(context, parent, 0.1f);
		this.stone = new Stone(context, parent, 1f);
		
		larry.setPos(stone.getWidth() + larry.getWidth(), Y_GROUND*parent.getHeight());
		larry.setIncX(RunLarry.LARRY_STARTSPEED); 
		larry.setIncY(0);
		stone.setPos(-stone.getWidth()/10, S_GROUND*parent.getHeight());
		stone.setIncX(Stone.STONE_SPEED);
		stone.setIncY(0);
		
		
		this.setStageBackground(BG_RES);
	}
		
	@Override
	public void onDrawStage(Canvas canvas) {
		larry.drawSprite(canvas);
		stone.drawSprite(canvas);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		larry.setPos(stone.getWidth() + larry.getWidth(), Y_GROUND*parent.getHeight());
		stone.setPos(-stone.getWidth()/2, S_GROUND*parent.getHeight());
		
	}
	
	/*
	 * Called when user touches the GameView
	 */
	@Override
	protected void onTap() {		
		larry.doAction();
	}
	
	/*
	 * Called by game update thread 
	 */
	
	@Override
	protected void updatePhysics(double delay) {
		larry.updateLarry(delay);
		
		stone.setIncX(stone.getIncX()+0.05d);
		
		if(larry.distance(stone) < 150) killState();
		
		larry.incPos(delay);
		stone.incPos(delay);
		
		if(larry.getPosX() > parent.getWidth()-larry.getWidth()/2) finishStage();
	}

	private void killState(){

		larry.isKilled();
		stone.setPos(-stone.getWidth()/10, S_GROUND*parent.getHeight());
		larry.setPos(stone.getWidth() + larry.getWidth(), Y_GROUND*parent.getHeight());
		larry.setIncX(RunLarry.LARRY_STARTSPEED); 
		stone.setIncX(Stone.STONE_SPEED);

	}

	
	private class RunLarry extends Larry{
		
		/*
		 * Larry constants.
		 */
		public static final int LARRY_STARTSPEED = 3;
		public static final int LARRY_MAXVEL = 11;
		private SoundPool mSoundPool;
				
		private int deathSound;
			
		public RunLarry(Context context, View view, float scale) {
			super(context, view, scale);
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);
			deathSound = mSoundPool.load(context, LARRY_SOUND_DIE, 1);
			
		}
		
		@Override
		protected void doAction() {
			if(this.getIncX() < RunLarry.LARRY_MAXVEL){
				this.setIncX(this.getIncX()+ 0.5d);
			}
		}
		
		protected void isKilled(){
			if(parent.areGameSoundEffectsEnabled()) mSoundPool.play(deathSound, 1, 1, 1, 0, 1);
		}
		
	}


	@Override
	protected void restartStage() {
		// TODO Auto-generated method stub
		
	}
	
	public class Stone extends Sprite {
		public static final double STONE_SPEED = 7;

		// Resource names
		private static final int STONE_FRAME_1 = R.drawable.stone;
		
		public Stone(Context context, View view, float scale) {
			super(view, context.getResources().getDrawable(STONE_FRAME_1), scale);
		}
		
		public void updateStone(double delay){
			//TODO Blah rotate stone
		}
		
	}
}



