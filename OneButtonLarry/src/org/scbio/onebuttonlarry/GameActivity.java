package org.scbio.onebuttonlarry;

import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.GameView.OnGameListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class GameActivity extends Activity  implements OnGameListener{

	private GameView game;
	private ImageView pause;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_game);
		
		pause = (ImageView) findViewById(R.id.bigResumeIcon);
		game = (GameView) findViewById(R.id.GameView);
		game.setParent(this);
		game.setOnGameListener(this);
	}


	@Override
	protected void onResume() {
		super.onResume();
		game.resumeGame();
		MusicManager.resume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		game.finishGame();
	}

	@Override
	protected void onPause() {
		super.onPause();
		game.pauseGame();
	}


	@Override
	public void onBackPressed() {
		game.finishGame();
		super.onBackPressed();
	}
	
	public void onClickPause(View v){
		game.pauseGame();
		pause.setVisibility(View.VISIBLE);
	}
	
	public void onClickResume(View v){
		game.resumeGame();
		pause.setVisibility(View.GONE);
	}

	@Override
	public void onGameEnd(long taps) {
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("score", taps);
		setResult(RESULT_OK, returnIntent);
		finish();
	}


	@Override
	public void onGamePause() {

	}
}
