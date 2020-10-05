package com.shopping_point.vendor_shopping_point;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public AppPreferences(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(String.valueOf(R.string.s_pref_name),context.MODE_PRIVATE);
    editor=sharedPreferences.edit();
    }


    public void setLoginStatus( boolean status)
    {
        editor.putBoolean(String.valueOf(R.string.s_pref_login_status),status);
        editor.commit();

    }
    public boolean getLoginStatus()
    {
return sharedPreferences.getBoolean(String.valueOf(R.string.s_pref_login_status),false);

    }
    //Name
    public void setDiaplayName(String name)
    {

        editor.putString(String.valueOf(R.string.s_pref_login_name),name);
        editor.commit();

    }
    public String getDiaplayName()
    {

       return sharedPreferences.getString(String.valueOf(R.string.s_pref_login_name),"name");
    }



    //Email
    public void setDiaplayEmail(String email)
    {

        editor.putString(String.valueOf(R.string.s_pref_login_email),email);
        editor.commit();

    }
    public String getDiaplayEmail()
    {

        return sharedPreferences.getString(String.valueOf(R.string.s_pref_login_email),"email");
    }



}
