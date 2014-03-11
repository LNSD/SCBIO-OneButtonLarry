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

public class RunStopStage extends GameStage {

	/*
	 * Stage map constants.
	 */
	private static final int BG_RES = R.drawable.stagebg_runstop;
	private static final float Y_GROUND = 0.626f;
	private static final float Y_CEILING = 0.28f;

	private GameView parent;
	private StopLarry larry;
	private Arrow arrow1;
	private Arrow arrow2;
	private Arrow arrow3;

	public RunStopStage(Context context, GameView parent) {	
		this.parent = parent;

		larry = new StopLarry(context, parent, 0.1f);
		arrow1 = new Arrow(context, parent);
		arrow2 = new Arrow(context, parent);
		arrow3 = new Arrow(context, parent);

		restartStage();
		this.setStageBackground(BG_RES);
	}

	@Override
	public void onDrawStage(Canvas canvas) {
		larry.drawSprite(canvas);
		arrow1.drawSprite(canvas);
		arrow2.drawSprite(canvas);
		arrow3.drawSprite(canvas);
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
		if(!larry.isStopped()) larry.doAction();
	}

	/*
	 * Called by game update thread 
	 */

	@Override
	protected void updatePhysics(double delay) {
		larry.updateLarry(delay);
		
		arrow1.updateArrowState(larry);
		arrow2.updateArrowState(larry);
		arrow3.updateArrowState(larry);
			
		larry.updateWaiting(delay);
		
		// Update positions for every sprite.
		larry.incPos(delay);
		arrow1.incPos(delay);
		arrow2.incPos(delay);
		arrow3.incPos(delay);
 
		if(larry.getPosX() > parent.getWidth()-larry.getWidth()/2) finishStage();
	}
	
	@Override
	protected void restartStage(){
		larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
		arrow1.setPos(0.2*parent.getWidth(), Y_CEILING*parent.getHeight());
		arrow2.setPos(0.55*parent.getWidth(), Y_CEILING*parent.getHeight());
		arrow3.setPos(0.8*parent.getWidth(), Y_CEILING*parent.getHeight());
		larry.setIncX(StopLarry.LARRY_REGSPEED);
		arrow1.setIncY(0);
		arrow2.setIncY(0);
		arrow3.setIncY(0);
		
		arrow1.fall = false;
		arrow2.fall = false;
		arrow3.fall = false;
	}
	
	private class StopLarry extends Larry{

		/*
		 * Larry constants.
		 */
		public static final int LARRY_WAITNGTIME = 20;
		public static final int LARRY_REGSPEED = 6;
		
		private SoundPool mSoundPool;
		private int deathSound;
		
		private boolean stop = false;
		private int waiting = 0;
		
		/*
		 * Constructors
		 */
		public StopLarry(Context context, View view, float scale) {
			super(context, view, scale);
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);
			deathSound = mSoundPool.load(context, LARRY_SOUND_DIE, 1);
		}
		public StopLarry(Context context, View view) {
			super(context, view);
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);
			deathSound = mSoundPool.load(context, LARRY_SOUND_DIE, 1);
		}

		@Override
		protected void doAction() {
			this.stop = true;
			this.setIncX(0);
		}

		public boolean isStopped(){
			return this.stop;
		}
		
		public void updateWaiting(double delay)
		{	
			if(this.isStopped())
			{
				this.waiting += delay;
				
				if(this.waiting >= StopLarry.LARRY_WAITNGTIME){
					this.resume();
				}
			}
		}
		
		public void resume(){
			this.stop = false;
			this.waiting = 0;
			this.setIncX(StopLarry.LARRY_REGSPEED);
		}
		
		protected void killed(){
			if(parent.areGameSoundEffectsEnabled()) 
				mSoundPool.play(deathSound, 1, 1, 1, 0, 1);
		}

	}

	public class Arrow extends Sprite {

		// Resource names
		private static final int ARROW = R.drawable.arrow;

		public static final double ARROW_FALLSPEEDY = 21;
		public static final float LARRY_POXIMITY = 0.218f;

		private boolean fall = false;

		/*
		 * Constructors
		 */
		public Arrow(Context context, View view, float scale) {
			super(view, context.getResources().getDrawable(ARROW), scale);
		}
		public Arrow(Context context, View view) {
			super(view, context.getResources().getDrawable(ARROW));
		}

		public boolean isFalling(){
			return fall;
		}
		
		public void updateArrowState(StopLarry larry){
			if(!this.isFalling())
			{
				this.startFalling(larry);
			}else{
				if(this.getPosY() > Y_GROUND*parent.getHeight()+this.getHeight()) {
					this.fall = false;
				}else if(this.distance(larry) < this.getWidth()){
					restartStage();
					larry.killed();
				}
			}
		}
		
		public void startFalling(StopLarry larry)
		{	
			if(this.distance(larry) < Arrow.LARRY_POXIMITY*parent.getWidth()){
				this.fall = true;
				this.setIncY(Arrow.ARROW_FALLSPEEDY);
			}
		}

	}

}

