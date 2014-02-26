package org.scbio.onebuttonlarry.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class GameStage extends View {
	
	private static final int PROCESS_PERIOD = 50;
	
	private Activity parent;
	private GameThread thread;
	private Drawable background;
	
	private OnGamePauseListener onGamePauseListener;
	private OnStageFinishListener onStageFinishListener;

	public GameStage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setParent(Activity parent) {
		this.parent = parent;
	}
	
	@SuppressWarnings("deprecation")
	protected void setStageBackground(int bgResId){
		this.background = parent.getResources().getDrawable(bgResId);
		this.setBackgroundDrawable(background);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		onTouch();
		return super.onTouchEvent(event);	
	}
	
	/**
	 * Callback method. Called when Game view is touched.
	 * Perfect for audio effects.
	 */
	protected abstract void onTouch();

	/**
	 * Game Thread class.
	 * 
	 */

	protected class GameThread extends Thread{

		private boolean pause, running; 

		public synchronized void pauseGameThread() {
			pause = true;      
		}

		public synchronized void resumeGameThread() {
			pause = false;
			notify();
		}  

		public void finishGameThread() {
			running = false;
			if(pause) resumeGameThread();
		}

		@Override
		public void run() 
		{	
			try{
				running = true;
				while (running) 
				{
					updateGame();

					synchronized(this) 
					{
						while(pause) wait();
					}
				}
			}catch (Exception e){
				Log.e(GameThread.class.toString(), "GameThread run() crashes @wait()", e);
			}
		}

	}
	
	private long before = 0;
	
	private void updateGame(){
		long now = System.currentTimeMillis();
		
        // Processing period not completed. Do nothing.
        if (before + PROCESS_PERIOD > now) {
              return;
        }
        
        // Delay calculation. For real-time.          
        double delay = (now - before) / PROCESS_PERIOD;
        before = now; 		
		
		updatePhysics(delay);
	}
	
	protected abstract void updatePhysics(double delay);

	public void resumeGame(){
		this.thread.resumeGameThread();
	}

	public void pauseGame(){
		this.thread.pauseGameThread();
		this.onGamePauseListener.onPause();
	}

	public void finishGame(){
		this.thread.finishGameThread();
		this.onStageFinishListener.onFinish();
	}

	/**
	 * Callback interface.
	 * Called when game is paused.
	 */
	public interface OnGamePauseListener{
		public void onPause();
	}
	
	public void setOnGamePauseListener(OnGamePauseListener onGamePauseListener) {
		this.onGamePauseListener = onGamePauseListener;
	}

	public OnGamePauseListener getOnGamePauseListener() {
		return onGamePauseListener;
	}

	/**
	 * Callback method.
	 * Called when game finishes.
	 */
	public interface OnStageFinishListener{
		public void onFinish();
	}

	public void setOnStageFinishListener(OnStageFinishListener onStageFinishListener) {
		this.onStageFinishListener = onStageFinishListener;
	}

	public OnStageFinishListener getOnStageFinishListener() {
		return onStageFinishListener;
	}
	
}
