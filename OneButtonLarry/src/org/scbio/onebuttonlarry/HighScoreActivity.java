package org.scbio.onebuttonlarry;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HighscoreActivity extends ListActivity {

	private static LayoutInflater inflater = null;
	private ArrayList<Highscore> mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList = HighscoreManager.loadHighscores(getBaseContext());
		
		setListAdapter(new HighscoreListAdapter());
	}
	
	private class HighscoreListAdapter extends BaseAdapter{
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View vi=convertView;
				if(convertView==null)
					vi = inflater.inflate(R.layout.row_highscore, null);

				TextView player = (TextView) vi.findViewById(R.id.rowplayerTextView);
				TextView score = (TextView) vi.findViewById(R.id.rowscoreTextView);				
				Highscore rowData = mList.get(position);

				// Setting all values in listview
				player.setText(rowData.getPlayer());
				score.setText(String.valueOf(rowData.getScore()));
				return vi;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return mList.get(position);
			}

			@Override
			public int getCount() {
				return mList.size();
			}
	}


}
