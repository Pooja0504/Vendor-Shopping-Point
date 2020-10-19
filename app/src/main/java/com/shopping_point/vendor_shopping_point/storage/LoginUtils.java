package com.shopping_point.vendor_shopping_point.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.shopping_point.vendor_shopping_point.model.LoginApiResponse;
import com.shopping_point.vendor_shopping_point.model.Vendor;


public class LoginUtils {

    private static final String SHARED_PREF_NAME = "shared_preference";

    private static LoginUtils mInstance;
    private Context mCtx;

    private LoginUtils(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized LoginUtils getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new LoginUtils(mCtx);
        }
        return mInstance;
    }

    public void saveVendorInfo(LoginApiResponse response) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", response.getId());
        editor.putString("name", response.getOrganisation_name());
        editor.putString("email", response.getEmail());
        editor.putString("password", response.getPassword());
        editor.putString("phone_no",response.getPhone_no());
        editor.putString("token", response.getToken());
        editor.putBoolean("isActive", response.isActive());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public void saveVendorInfo(Vendor vendor) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", vendor.getId());
        editor.putString("name", vendor.getOrganisation_name());
        editor.putString("email", vendor.getEmail());
        editor.putString("password", vendor.getPassword());
        editor.putString("phone_no",vendor.getPhone_no());
        editor.putBoolean("isActive", vendor.isActive());
        editor.apply();
    }

    public Vendor getVendorInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Vendor(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("phone_no",null),
                sharedPreferences.getBoolean("isActive", false)
        );
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        editor.apply();
    }

}
