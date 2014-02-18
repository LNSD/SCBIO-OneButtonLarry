package org.scbio.onebuttonlarry;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends FragmentActivity implements ScoreDialog.ScoreDialogListener {

	ToggleButton toggleSound;
	ImageView dancingLarry;
	ImageView waitingLarry;
	MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toggleSound = (ToggleButton) findViewById(R.id.toggleSound);
		dancingLarry = (ImageView) findViewById(R.id.imageDancingLarry);
		waitingLarry = (ImageView) findViewById(R.id.imageWaitingLarry);
		mediaPlayer = MediaPlayer.create(this,R.raw.gamemusic);

		setUpMediaPlayerandLarry();
		
		toggleSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {       
				if (isChecked) {
					startLarryAndMusic();
				} else {
					stopLarryAndMusic();
				}
			}
		});
	}
	
	
	/*
	 * Animation and MediaPlayer methods. 
	 */
	
	private void setUpMediaPlayerandLarry() {
		dancingLarry.setVisibility(View.VISIBLE); 
		waitingLarry.setVisibility(View.GONE);
		
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}
	
	private void startLarryAndMusic() {
		dancingLarry.setVisibility(View.VISIBLE); 
		waitingLarry.setVisibility(View.GONE);       
		
		mediaPlayer.seekTo(0);                 
		mediaPlayer.start();
	}
	
	private void stopLarryAndMusic() {
		waitingLarry.setVisibility(View.VISIBLE);
		dancingLarry.setVisibility(View.GONE);
		
		if(mediaPlayer.isPlaying())
					mediaPlayer.pause();
	}
	
	private void finishMusic(){
		mediaPlayer.setLooping(false);
		mediaPlayer.stop();
		mediaPlayer.release();
	}
	
	@Override
	protected void onStop() {
		finishMusic();
		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 100:
			if (resultCode==RESULT_OK) {
				ScoreDialog mScoreDialog= new ScoreDialog();
				mScoreDialog.setScore(data.getExtras().getLong("puntuacion"));
				mScoreDialog.show(getSupportFragmentManager(), "score");
				
			}else{
				Log.e(MainActivity.class.toString(), "Unknown 'resultCode' response: "+resultCode);
			}
			break;

		default:
			Log.e(MainActivity.class.toString(), "Unknown 'requestCode' response: "+requestCode);
			break;
		}		

	}

	@Override
	public void onDialogSubmitClick(DialogFragment dialog, CharSequence player, long score) {
		//TODO Store score to DB
		Toast.makeText(
				getApplicationContext(), 
				"Score stored = "+player+':'+String.valueOf(score), 
				Toast.LENGTH_SHORT).show();	
	}

	public void onClickGame(View v) {
		Intent gameIntent = new Intent(getBaseContext(), GameActivity.class);
		startActivityForResult(gameIntent, 100);
	}

	public void startAboutUs(View view){
		Intent aboutUs = new Intent(this, AboutActivity.class);
		startActivity(aboutUs);
	}
	
	public void onClickExit(View view){
		setResult(RESULT_OK);
		mediaPlayer.release();
		finish();
	}

}

