package org.scbio.onebuttonlarry;

import org.scbio.onebuttonlarry.game.GameView;
import org.scbio.onebuttonlarry.game.GameView.OnGameListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class GameActivity extends Activity  implements OnGameListener{

	private GameView game;
	private ImageView pause;
	ToggleButton toggleButtonSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_game);
		
		toggleButtonSound = (ToggleButton) findViewById(R.id.toggleButtonMusic);
		toggleButtonSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				PreferencesManager.storeMusicPreference(getBaseContext(), isChecked);
				if(isChecked)
					MusicManager.start(getApplicationContext(), R.raw.gamemusic);
				else
					MusicManager.release();
			}
		});
		
		pause = (ImageView) findViewById(R.id.bigResumeIcon);
		game = (GameView) findViewById(R.id.GameView);
		game.setParent(this);
		game.setOnGameListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		boolean musicState = PreferencesManager.loadMusicPreference(getBaseContext());
		if(musicState)
			MusicManager.start(getApplicationContext(), R.raw.gamemusic);
		
		toggleButtonSound.setChecked(musicState);
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
		MusicManager.pause();
		pause.setVisibility(View.VISIBLE);
	}
	
	public void onClickResume(View v){
		game.resumeGame();
		MusicManager.resume();
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
