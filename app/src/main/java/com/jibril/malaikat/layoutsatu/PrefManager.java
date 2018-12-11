package com.jibril.malaikat.layoutsatu;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "sharedPreference";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    //data yang di simpan
    private static final String EMAIL = "email";

    public PrefManager(Context _context){
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //njajal
    public void setEmail(String email){
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getEmail(){
        return pref.getString(EMAIL, "");
    }
}
