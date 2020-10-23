package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.shopping_point.vendor_shopping_point.R;

public class NavNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_notification);
        //Toast.makeText(this, "Notification Opened", Toast.LENGTH_SHORT).show();
    }
}