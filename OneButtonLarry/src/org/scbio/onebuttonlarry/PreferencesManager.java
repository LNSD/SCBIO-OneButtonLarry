package org.scbio.onebuttonlarry;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
	
	public static void storeMusicPreference(Context context, boolean state){
		SharedPreferences pref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("MusicState", state);
        editor.commit();
	}
	
	public static boolean loadMusicPreference(Context context){
		SharedPreferences pref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);	
		return pref.getBoolean("MusicState", true);
	}
}
