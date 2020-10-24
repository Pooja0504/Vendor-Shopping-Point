package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;


import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.MyProductAdapter;
import com.shopping_point.vendor_shopping_point.databinding.ActivityMyProductBinding;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.viewModel.MyProductViewModel;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;
import static com.shopping_point.vendor_shopping_point.view.AuthenticationActivity.isActivityRunning;

public class MyProductActivity extends AppCompatActivity {



    private ActivityMyProductBinding binding;
    private static final String TAG = "MyProductActivity";
    private MyProductViewModel myProductViewModel;
    private MyProductAdapter myProductAdapter;
    public static boolean isActivityRunning = false;

    @Override
    protected void onStart() {
        super.onStart();
        isActivityRunning=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivityRunning=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_product);

        myProductViewModel = ViewModelProviders.of(this).get(MyProductViewModel.class);

        setUpRecyclerView();
        int seller_id = LoginUtils.getInstance(getApplication()).getVendorInfo().getId();
        getMyProduct(seller_id);

    }
    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.productlist.setLayoutManager(layoutManager);
        binding.productlist.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.productlist.addItemDecoration(dividerItemDecoration);
    }

    private void getMyProduct(int seller_id) {
        myProductViewModel.getMyProduct(seller_id).observe(this, MyProductResponse -> {
            myProductAdapter = new MyProductAdapter(getApplicationContext(), MyProductResponse.getMyProduct(seller_id));
            binding.productlist.setAdapter(myProductAdapter);
            myProductAdapter.notifyDataSetChanged();
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}
