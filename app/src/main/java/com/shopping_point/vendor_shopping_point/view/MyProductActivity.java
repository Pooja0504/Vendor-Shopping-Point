package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;


import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.MyProductAdapter;
import com.shopping_point.vendor_shopping_point.databinding.ActivityMyProductBinding;
import com.shopping_point.vendor_shopping_point.viewModel.MyProductViewModel;

public class MyProductActivity extends AppCompatActivity {



    private static final String TAG = "MyProductActivity";
    private ActivityMyProductBinding binding;
    private MyProductViewModel myProductViewModel;
    private MyProductAdapter myProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_product);

        myProductViewModel = ViewModelProviders.of(this).get(MyProductViewModel.class);

        setUpRecyclerView();

        getPosters();
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.productlist.setLayoutManager(layoutManager);
        binding.productlist.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.productlist.addItemDecoration(dividerItemDecoration);
    }

    private void getPosters() {
        myProductViewModel.getMyproduct().observe(this, MyProductResponse -> {
            myProductAdapter = new MyProductAdapter(getApplicationContext(), MyProductResponse.getMyProduct());
           // binding.productlist.setAdapter(product_name,product_price,product_description);
            myProductAdapter.notifyDataSetChanged();
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}
