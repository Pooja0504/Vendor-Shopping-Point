package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;


import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.MyProductAdapter;
import com.shopping_point.vendor_shopping_point.databinding.ActivityActivateProductBinding;
import com.shopping_point.vendor_shopping_point.databinding.ActivityMyProductBinding;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.viewModel.MyProductViewModel;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;

public class ActivateProductActivity extends AppCompatActivity {



    private ActivityActivateProductBinding binding;
    private static final String TAG = "ActivateProductActivity";
    private MyProductViewModel myProductViewModel;
    private MyProductAdapter myProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activate_product);
        myProductViewModel = ViewModelProviders.of(this).get(MyProductViewModel.class);

        setUpRecyclerView();
        int seller_id = LoginUtils.getInstance(getApplication()).getVendorInfo().getId();
        getMyProduct(seller_id);
    }
    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.activateproduct.setLayoutManager(layoutManager);
        binding.activateproduct.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.activateproduct.addItemDecoration(dividerItemDecoration);
    }

    private void getMyProduct(int seller_id) {
        myProductViewModel.getMyProduct(seller_id).observe(this, MyProductResponse -> {
            myProductAdapter = new MyProductAdapter(getApplicationContext(), MyProductResponse.getMyProduct(seller_id));
            binding.activateproduct.setAdapter(myProductAdapter);
            myProductAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
