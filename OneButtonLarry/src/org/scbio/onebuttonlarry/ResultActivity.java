package org.scbio.onebuttonlarry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		mIntent = new Intent();
		mIntent.putExtras(getIntent().getExtras());		
		
		long score = getIntent().getExtras().getLong("score");
		((TextView) findViewById(R.id.scoreTextView)).setText(String.valueOf(score));		
		((Button) findViewById(R.id.buttonSubmit)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String player = ((EditText) findViewById(R.id.scorenameEditText)).getText().toString();
				
				if(player.length()<1){
					player = "NONAME";
				}
				
				mIntent.putExtra("player", player);
				setResult(RESULT_OK, mIntent);
				finish();
			}
		});
	}
}
