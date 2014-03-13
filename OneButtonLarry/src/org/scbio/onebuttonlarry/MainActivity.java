package org.scbio.onebuttonlarry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	ToggleButton toggleSound;
	ImageView dancingLarry;
	ImageView waitingLarry;
	MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		toggleSound = (ToggleButton) findViewById(R.id.toggleSound);
		dancingLarry = (ImageView) findViewById(R.id.imageDancingLarry);
		waitingLarry = (ImageView) findViewById(R.id.imageWaitingLarry);

		toggleSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				swapLarryAnimation(isChecked);
				PreferencesManager.storeMusicPreference(getBaseContext(), isChecked);
				if(isChecked)
					MusicManager.start(getApplicationContext(), R.raw.gamemusic);
				else
					MusicManager.release();
			}
		});
	}

	private void swapLarryAnimation(boolean state) {
		if (state) {
			dancingLarry.setVisibility(View.VISIBLE);
			waitingLarry.setVisibility(View.GONE);
		} else {
			waitingLarry.setVisibility(View.VISIBLE);
			dancingLarry.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) 
		{
		case 100:
			if (resultCode==RESULT_OK) 
			{
				Intent mIntent = new Intent(getApplicationContext(), ResultActivity.class);
				mIntent.putExtras(data.getExtras());
				startActivityForResult(mIntent, 200);
			}else{
				Log.w(TAG, "Unknown 'resultCode' response: "+resultCode);
			}
			break;
			
		case 200:
			if (resultCode==RESULT_OK) 
			{
				String player = data.getExtras().getString("player");
				long score = data.getExtras().getLong("score");
				if(HighscoreManager.updateHighscores(getBaseContext(), new Highscore(player, score)))
				{
					Log.i(TAG, "Highscore stored successfully. Player:"+player.toString()+"/Score:"+score);
				}
			}else{
				Log.w(TAG, "Unknown 'resultCode' response: "+resultCode);
			}
			break;
			
		default:
			Log.w(TAG, "Unknown 'requestCode' response: "+requestCode);
			break;
		}		
	}

	@Override
	protected void onStart() {
		super.onStart();

		boolean musicState = PreferencesManager.loadMusicPreference(getBaseContext());
		if(musicState)
			MusicManager.start(getApplicationContext(), R.raw.gamemusic);

		toggleSound.setChecked(musicState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		final AnimationDrawable anim;
		
		swapLarryAnimation(toggleSound.isChecked());
		MusicManager.resume();
		
		if(toggleSound.isChecked()){
			anim = (AnimationDrawable) dancingLarry.getBackground();
			dancingLarry.post(new Runnable() {
			    public void run() {
			        if ( anim != null ) anim.start();
			      }
			});
		}else{
			anim = (AnimationDrawable) waitingLarry.getBackground();
			waitingLarry.post(new Runnable() {
			    public void run() {
			        if ( anim != null ) anim.start();
			      }
			});
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		MusicManager.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MusicManager.release();
	}

	public void onClickGame(View v) {
		Intent gameIntent = new Intent(getBaseContext(), GameActivity.class);
		startActivityForResult(gameIntent, 100);
	}

	public void onClickAboutUs(View view){
		Intent aboutUs = new Intent(this, AboutActivity.class);
		startActivity(aboutUs);
	}

	public void onClickHighscore(View view){
		Intent highScore = new Intent(this, HighscoreActivity.class);
		startActivity(highScore);
	}

	public void onClickExit(View view){
		setResult(RESULT_OK);
		finish();
	}

}