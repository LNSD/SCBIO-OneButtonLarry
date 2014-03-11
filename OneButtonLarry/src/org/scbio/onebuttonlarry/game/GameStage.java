package org.scbio.onebuttonlarry.game;

import android.graphics.Canvas;

public abstract class GameStage {
	
	private OnStageFinishListener onStageFinishListener;
	private int background;

	public GameStage(){}
	
	/**
	 * Callback method. Called when Game view is touched.
	 * Perfect for audio effects.
	 */
	protected abstract void onTap();
	
	/*
	 * Stage updatePhysics method.
	 */
	protected abstract void updatePhysics(double delay);
	
	protected abstract void onDrawStage(Canvas canvas);
	
	protected abstract void onSizeChanged(int w, int h, int oldw, int oldh);
	
	protected abstract void restartStage();
	
	protected void finishStage(){
		if(onStageFinishListener!=null)
			onStageFinishListener.onStageFinish();
	}
	
	
	/**
	 * Callback method.
	 * Called when game finishes.
	 */
	public interface OnStageFinishListener{
		public void onStageFinish();
	}

	public void setOnStageFinishListener(OnStageFinishListener onStageFinishListener) {
		this.onStageFinishListener = onStageFinishListener;
	}

	public OnStageFinishListener getOnStageFinishListener() {
		return onStageFinishListener;
	}
	
	/*
	 * Background getters and setters
	 */
	protected void setStageBackground(int bgResId){
		this.background = bgResId;
	}
	protected int getStageBackground(){
		return this.background;
	}
	
}
