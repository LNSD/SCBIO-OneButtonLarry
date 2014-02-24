package org.scbio.onebuttonlarry;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class MainActivity extends FragmentActivity implements ScoreDialog.ScoreDialogListener {

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
				if(isChecked)
					MusicManager.resume();
				else
					MusicManager.pause();
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
		switch (requestCode) {
		case 100:
			if (resultCode==RESULT_OK) 
			{
				ScoreDialog mScoreDialog = new ScoreDialog();
				mScoreDialog.setScore(data.getExtras().getLong("score"));
				mScoreDialog.show(getSupportFragmentManager(), "score");
			}else{
				Log.e(TAG, "Unknown 'resultCode' response: "+resultCode);
			}
			break;

		default:
			Log.e(TAG, "Unknown 'requestCode' response: "+requestCode);
			break;
		}		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		swapLarryAnimation(toggleSound.isChecked());
	}

	@Override
	protected void onStart() {
		super.onStart();
		MusicManager.start(getApplicationContext(), R.raw.gamemusic);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MusicManager.release();
	}

	@Override
	public void onDialogSubmitClick(DialogFragment dialog, CharSequence player, long score) 
	{
		if(HighscoreManager.updateHighscores(getBaseContext(), new Highscore(player.toString(), score))){
			Log.i(TAG, "Highscore stored successfully. Player:"+player.toString()+"/Score:"+score);
		}
	}

	public void onClickGame(View v) {
		Intent gameIntent = new Intent(getBaseContext(), GameActivity.class);
		startActivityForResult(gameIntent, 100);
	}

	public void startAboutUs(View view){
		Intent aboutUs = new Intent(this, AboutActivity.class);
		startActivity(aboutUs);
	}
	
	public void onClickHighscore(View view){
		Intent highScore = new Intent(this, HighscoreActivity.class);
		startActivity(highScore);
	}
	

	public void onClickExit(View view){
		setResult(RESULT_OK);
		MusicManager.release();
		finish();
	}

}