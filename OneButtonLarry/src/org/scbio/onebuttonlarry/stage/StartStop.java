package org.scbio.onebuttonlarry.stage;

import org.scbio.onebuttonlarry.R;
import org.scbio.onebuttonlarry.game.GameStage;
import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.Larry;
import org.scbio.onebuttonlarry.game.Sprite;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;

public class StartStop extends GameStage {

	/*
	 * Stage map constants.
	 */
	private static final int BG_RES = R.drawable.stagebg_startstop;
	private static final float Y_GROUND = 0.626f;
	private static final float Y_CEILING = 0.28f;
	
	private GameView parent;
	private StopLarry larry;
	private FallArrow arrow1;
	private FallArrow arrow2;
	private FallArrow arrow3;
	private int count = 0;
	

	public StartStop(Context context, GameView parent) {	
		this.parent = parent;

		larry = new StopLarry(context, parent, 0.1f);
		arrow1 = new FallArrow(context, parent, 1f);
		arrow2 = new FallArrow(context, parent, 1f);
		arrow3 = new FallArrow(context, parent, 1f);
		
		larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
		arrow1.setPos(0.2*parent.getWidth(), Y_CEILING*parent.getHeight());
		arrow2.setPos(0.55*parent.getWidth(), Y_CEILING*parent.getHeight());
		arrow3.setPos(0.8*parent.getWidth(), Y_CEILING*parent.getHeight());
		larry.setIncX(StopLarry.LARRY_REGSPEED);
		larry.setIncY(0);
		arrow1.setIncY(0);
		arrow2.setIncY(0);
		arrow3.setIncY(0);
		
		
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
		larry.setPos(-larry.getWidth()/2, Y_GROUND*parent.getHeight());
		arrow1.setPos(0.2*parent.getWidth(), Y_CEILING*parent.getHeight());
		arrow2.setPos(0.55*parent.getWidth(), Y_CEILING*parent.getHeight());
		arrow3.setPos(0.8*parent.getWidth(), Y_CEILING*parent.getHeight());
		
	}
	
	/*
	 * Called when user touches the GameView
	 */
	@Override
	protected void onTap() {		
		if(!larry.isStoped()) larry.doAction();
	}
	
	/*
	 * Called by game update thread 
	 */
	
	@Override
	protected void updatePhysics(double delay) {
		larry.updateLarry(delay);

		startFalling();
		
		arrowFallen();
		
		killState();
		
		if(larry.isStoped()) {
			larry.setIncX(0);
			count += delay;
		}
		
		if(count >= StopLarry.LARRY_STOPSPEEDX)
		{
			larry.stop = false;
			count = 0;
			larry.setIncX(StopLarry.LARRY_REGSPEED);
		}
		
		larry.incPos(delay);
		arrow1.incPos(delay);
		arrow2.incPos(delay);
		arrow3.incPos(delay);
		
		if(larry.getPosX() > parent.getWidth()-larry.getWidth()/2) finishStage();
	}

	private void killState() {
		if(larry.getPosX() > 0.16*parent.getWidth() && larry.getPosX() < 0.23*parent.getWidth() && arrow1.isFalling()) {

			if(arrow1.getPosY() > Y_GROUND*parent.getHeight()) {
				larry.setPosX(-larry.getWidth()/2);
				arrow1.setIncY(0);
				arrow1.setPosY(Y_CEILING*parent.getHeight());
				arrow1.isKilled();
			}
		}
		

		if(larry.getPosX() > 0.51*parent.getWidth() && larry.getPosX() < 0.58*parent.getWidth() && arrow2.isFalling()) {

			if(arrow2.getPosY() > Y_GROUND*parent.getHeight()) {
				larry.setPosX(-larry.getWidth()/2);
				arrow1.setIncY(0);
				arrow1.setPosY(Y_CEILING*parent.getHeight());
				arrow2.setIncY(0);
				arrow2.setPosY(Y_CEILING*parent.getHeight());
				arrow2.isKilled();

			}

		}

		if(larry.getPosX() > 0.76*parent.getWidth() && larry.getPosX() < 0.83*parent.getWidth() && arrow3.isFalling()) {

			if(arrow3.getPosY() > Y_GROUND*parent.getHeight()) {
				larry.setPosX(-larry.getWidth()/2);
				arrow1.setIncY(0);
				arrow1.setPosY(Y_CEILING*parent.getHeight());
				arrow2.setIncY(0);
				arrow2.setPosY(Y_CEILING*parent.getHeight());
				arrow3.setIncY(0);
				arrow3.setPosY(Y_CEILING*parent.getHeight());
				arrow3.isKilled();
			}
			
		}
	}

	private void arrowFallen() {
		if(arrow1.getPosY() > 1.2*Y_GROUND*parent.getHeight()) {
			arrow1.fall = false;
		}
		
		if(arrow2.getPosY() > 1.2*Y_GROUND*parent.getHeight()) {
			arrow2.fall = false;
		}
		
		if(arrow3.getPosY() > 1.2*Y_GROUND*parent.getHeight()) {
			arrow3.fall = false;
		}
	}

	private void startFalling() {
		if(larry.getPosX() > 0.14*parent.getWidth()) {
			arrow1.setIncY(FallArrow.ARROW_FALLSPEEDY);
			arrow1.doFall();
		}
		if(larry.getPosX() > 0.49*parent.getWidth()) {
			arrow2.setIncY(FallArrow.ARROW_FALLSPEEDY);
			arrow2.doFall();
		}
		if(larry.getPosX() > 0.74*parent.getWidth()) {
			arrow3.setIncY(FallArrow.ARROW_FALLSPEEDY);
			arrow3.doFall();
		}
	}
	
	private class StopLarry extends Larry{
		
		/*
		 * Larry constants.
		 */
		public static final int LARRY_REGSPEED = 6;
		public static final int LARRY_STOPSPEEDX = 20;
				
		private boolean stop = false;
			
		public StopLarry(Context context, View view, float scale) {
			super(context, view, scale);
			
		}
		
		@Override
		protected void doAction() {
			stop = true;
		}
		
		public boolean isStoped(){
			return stop;
		}
		
	}

	private class FallArrow extends Arrow{

		public static final double ARROW_FALLSPEEDY = 21;
		private SoundPool mSoundPool;

		private boolean fall = false;
		private int deathSound;
		
		public FallArrow(Context context, View view, float scale) {
			super(context, view, scale);
			mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);
			deathSound = mSoundPool.load(context, LARRY_SOUND_DIE, 1);
		}

		@Override
		protected void doFall() {
			fall = true;
		}
		
		public boolean isFalling(){
			return fall;
		}
		
		protected void isKilled(){
			if(parent.areGameSoundEffectsEnabled()) mSoundPool.play(deathSound, 1, 1, 1, 0, 1);
		}
	}
	
	public abstract class Arrow extends Sprite {

		// Resource names
		private static final int ARROW = R.drawable.arrow;
		public static final int LARRY_SOUND_DIE = R.raw.deathsound;
		
		// Frame drawable
		private Drawable ArrowFrame;
		
		public Arrow(Context context, View view, float scale) {
			super(view, context.getResources().getDrawable(ARROW), scale);
			
			ArrowFrame = context.getResources().getDrawable(ARROW);
			
			setDrawable(ArrowFrame);
		}
		
		protected abstract void doFall();
		
	}

}

