package com.shopping_point.vendor_shopping_point;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    Thread timer;
    ImageView flash_logo;
    public static AppPreferences appPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        flash_logo = findViewById(R.id.flash_scr_logo_imgv);
        appPreferences=new AppPreferences(this);

        timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(appPreferences.getLoginStatus())
                    {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("name",appPreferences.getDiaplayName()).putExtra("email",appPreferences.getDiaplayEmail()));

                    }else {

                        Intent intent_my = new Intent(getApplicationContext(), Registration.class);
                        startActivity(intent_my);

                    }

                }

            }

        };
        timer.start();
    }
}