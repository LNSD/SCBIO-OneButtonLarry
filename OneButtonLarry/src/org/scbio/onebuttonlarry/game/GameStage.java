package org.scbio.onebuttonlarry.game;

import android.graphics.drawable.Drawable;
import android.util.Log;

public abstract class GameStage {
	
	private GameView parent;
	private OnStageFinishListener onStageFinishListener;
	private Drawable background;
	
	protected long taps = 0;

	public GameStage(GameView parent) {
		this.parent = parent;
	}

	@SuppressWarnings("deprecation")
	protected void setStageBackground(int bgResId){
		try {
			this.background = parent.getResources().getDrawable(bgResId); 
		} catch (Exception e) {
			Log.e("GAMESTAGE", "Crashed at getResources() from View subclass",e);
		}
		parent.setBackgroundDrawable(background);
	}
	
	/**
	 * Callback method. Called when Game view is touched.
	 * Perfect for audio effects.
	 */
	protected abstract void onTap();
	
	/*
	 * Stage updatePhysics method.
	 */
	protected abstract void updatePhysics(double delay);
	
	/**
	 * Callback method.
	 * Called when game finishes.
	 */
	public interface OnStageFinishListener{
		public void onStageFinish(long taps);
	}

	public void setOnStageFinishListener(OnStageFinishListener onStageFinishListener) {
		this.onStageFinishListener = onStageFinishListener;
	}

	public OnStageFinishListener getOnStageFinishListener() {
		return onStageFinishListener;
	}
	
	
	
}
