package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.OrdersAdapter;
import com.shopping_point.vendor_shopping_point.databinding.ActivityOrdersBinding;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.viewModel.OrdersViewModel;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;

public class OrdersActivity extends AppCompatActivity {
    private ActivityOrdersBinding binding;
    private static final String TAG = "OrdersActivity";
    private OrdersViewModel ordersViewModel;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);

        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);

        setUpRecyclerView();
        int seller_id = LoginUtils.getInstance(getApplication()).getVendorInfo().getId();
        getMyProduct(seller_id);

    }
    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.orderlist.setLayoutManager(layoutManager);
        binding.orderlist.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.orderlist.addItemDecoration(dividerItemDecoration);
    }

    private void getMyProduct(int seller_id) {
        ordersViewModel.getOrdersDetails(seller_id).observe(this, MyProductResponse -> {
            if(MyProductResponse==null)
            {
                Toast.makeText(this, " NULL OBJECT ", Toast.LENGTH_SHORT).show();
            }else
            {
                ordersAdapter = new OrdersAdapter(getApplicationContext(), MyProductResponse.getOrders(),this);
                binding.orderlist.setAdapter(ordersAdapter);
                ordersAdapter.notifyDataSetChanged();
            }

        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}