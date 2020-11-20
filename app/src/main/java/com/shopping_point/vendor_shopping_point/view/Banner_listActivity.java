package com.shopping_point.vendor_shopping_point.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.GetBannersAdapter;
import com.shopping_point.vendor_shopping_point.databinding.ActivityBannerListBinding;
import com.shopping_point.vendor_shopping_point.viewModel.GetBannersViewModel;


public class Banner_listActivity extends AppCompatActivity {

    private static final String TAG = "NewsFeedActivity";
    private ActivityBannerListBinding binding;
    private GetBannersViewModel newsFeedViewModel;
    private GetBannersAdapter newsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_banner_list);

        newsFeedViewModel = ViewModelProviders.of(this).get(GetBannersViewModel.class);

        setUpRecyclerView();

        getPosters();
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.banneritemlist.setLayoutManager(layoutManager);
        binding.banneritemlist.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.banneritemlist.addItemDecoration(dividerItemDecoration);
    }

    private void getPosters() {
        newsFeedViewModel.getPosters1().observe(this, NewsFeedResponse -> {
            newsFeedAdapter = new GetBannersAdapter(getApplicationContext(), NewsFeedResponse.getBanners(),this);
            binding.banneritemlist.setAdapter(newsFeedAdapter);
            newsFeedAdapter.notifyDataSetChanged();
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
