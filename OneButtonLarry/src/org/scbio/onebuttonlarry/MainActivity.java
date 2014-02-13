package org.scbio.onebuttonlarry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	Button buttonPlay;
	Button buttonAbout;
	Button buttonHighScore;
	Button buttonExit;
	ToggleButton toggleSoundn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonPlay = (Button) findViewById(R.id.buttonPlay);		
		buttonPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent gameIntent = new Intent(getBaseContext(), GameActivity.class);
				startActivityForResult(gameIntent, 100);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		
			switch (requestCode) {
			case 100:
				if (resultCode==RESULT_OK) {
					String result = data.getExtras().getString("puntuacion");
					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				}else{
					Log.e(MainActivity.class.toString(), "Unknown 'resultCode' response: "+requestCode);
				}
				break;

			default:
				Log.e(MainActivity.class.toString(), "Unknown 'requestCode' response: "+requestCode);
				break;
			}		
		
	}



}

