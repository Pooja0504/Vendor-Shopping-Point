package com.shopping_point.vendor_shopping_point.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.MyProductAdapter;
import com.shopping_point.vendor_shopping_point.adapter.NewsFeedAdapter;
import com.shopping_point.vendor_shopping_point.adapter.NotificationAdapter;
import com.shopping_point.vendor_shopping_point.databinding.ActivityHelpBinding;
import com.shopping_point.vendor_shopping_point.databinding.ActivityNavNotificationBinding;
import com.shopping_point.vendor_shopping_point.model.NotificationResponse;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;
import com.shopping_point.vendor_shopping_point.viewModel.MyProductViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.NewsFeedViewModel;
import com.shopping_point.vendor_shopping_point.viewModel.NotificationViewModel;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;

public class NavNotification extends AppCompatActivity {
    private ActivityNavNotificationBinding binding;
    private NotificationViewModel notificationViewModel;
    private NotificationAdapter notificationAdapter;
    int seller_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nav_notification);
        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.notifications));
        setUpRecyclerView();
         seller_id = LoginUtils.getInstance(getApplication()).getVendorInfo().getId();
        getNotification(seller_id);
    }

    private void getNotification(int seller_id) {
        notificationViewModel.getNotification(seller_id).observe(this, NotificationResponse -> {
            notificationAdapter = new NotificationAdapter(getApplicationContext(), NotificationResponse.getNotification(seller_id),this);
            binding.notification.setAdapter(notificationAdapter);
            notificationAdapter.notifyDataSetChanged();
        });
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.notification.setLayoutManager(layoutManager);
        binding.notification.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.notification.addItemDecoration(dividerItemDecoration);
    }

}