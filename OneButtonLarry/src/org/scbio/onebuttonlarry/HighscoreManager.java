package org.scbio.onebuttonlarry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class HighscoreManager {
	
	private static final String TAG = HighscoreManager.class.toString();
	
	public static boolean updateHighscores(Context context, Highscore highscore){
		ArrayList<Highscore> list = loadHighscores(context);
		list.add(highscore);
		return storeHighscore(context, list);
	}
	
	public static boolean storeHighscore(Context context, ArrayList<Highscore> list){
		SharedPreferences mSharedPreferences = context.getSharedPreferences("highscores", Context.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = mSharedPreferences.edit();
		
		prefEditor.putString("highscores", parseHighscoreArray(list));
		return prefEditor.commit();
	}
	
	public static ArrayList<Highscore> loadHighscores(Context context){
		SharedPreferences mSharedPreferences = context.getSharedPreferences("highscores", Context.MODE_PRIVATE);
		return parseHighscoreJSON(mSharedPreferences.getString("highscores", ""));
	}
	
	public static ArrayList<Highscore> parseHighscoreJSON(String jsonStr) {

		ArrayList<Highscore> result = new ArrayList<Highscore>();
		JSONObject scores;
		
		try {
			scores = new JSONObject(jsonStr);
			JSONArray list = scores.getJSONArray("highscores");
			for (int i=0; i<list.length(); i++) {
				JSONObject player = list.getJSONObject(i);
				String name = player.getString("player");
				long score = player.getInt("score");
				
				result.add(new Highscore(name, score));
			}
		} catch (JSONException e) {
			Log.e(TAG, "Error @ parseHighscoreJSON()");
			e.printStackTrace();
		}

		return result;
	}

	public static String parseHighscoreArray(ArrayList<Highscore> list){
		
		Collections.sort(list, new Comparator<Highscore>() {
			public int compare(Highscore o1, Highscore o2){
				if(o1.getScore()== o2.getScore())
					return 0;
				return o1.getScore() < o2.getScore() ? -1 : 1;
			}
		});
		
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; (i<5)&(i<list.size()); i++) 
		{
			JSONObject highscore = new JSONObject();
			try {
				highscore.put("player", list.get(i).getPlayer());
				highscore.put("score", list.get(i).getScore());
				jsonArray.put(highscore);
			} 
			catch (JSONException e) 
			{
				Log.e(TAG, "Error @ parseHighscoreArray()");
				e.printStackTrace();
			}
		}

		JSONObject scoresObj = new JSONObject();
		try {
			scoresObj.put("highscores", jsonArray);
		} catch (JSONException e) {
			Log.e(TAG, "Error @ parseHighscoreArray()");
			e.printStackTrace();
		}

		return(scoresObj.toString());
	}

}
