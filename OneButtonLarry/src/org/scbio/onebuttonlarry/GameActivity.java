package org.scbio.onebuttonlarry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
	}

	@Override
	public void onBackPressed() {
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("puntuacion", 1223l); //TODO Test code
		setResult(RESULT_OK, returnIntent);
		finish();
		super.onBackPressed();
	}

}
