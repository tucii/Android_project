package com.example.atalante.sos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by atalante on 20/12/2017.
 */

public class SharedPrefs {

    SharedPreferences sp;           // For Reading
    SharedPreferences.Editor edit;  // For Writing

    // Object Constructor

    public SharedPrefs(Context c)
    {
        sp = PreferenceManager.getDefaultSharedPreferences(c);
        edit = sp.edit();
    }

    public void setString(String key, String val)
    {
        edit.putString(key, val).commit();
    }

    public void setInt(String key, int val)
    {
        edit.putInt(key, val).commit();
    }

    public String getString(String key)
    {
        return sp.getString(key,"");
    }

    public int getInt(String key)
    {
        return sp.getInt(key, -1);
    }
}
