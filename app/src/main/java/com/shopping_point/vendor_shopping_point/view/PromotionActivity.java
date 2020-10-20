package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.databinding.ActivityPromotionBinding;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;

public class PromotionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
       ActivityPromotionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_promotion);
    }
}