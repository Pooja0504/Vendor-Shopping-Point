package com.shopping_point.vendor_shopping_point.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.shopping_point.vendor_shopping_point.R;
import com.shopping_point.vendor_shopping_point.adapter.CategoryAdapter;
import com.shopping_point.vendor_shopping_point.databinding.ActivityAddCategoryBinding;
import com.shopping_point.vendor_shopping_point.model.Category;
import com.shopping_point.vendor_shopping_point.viewModel.CategoryViewModel;

import static com.shopping_point.vendor_shopping_point.storage.LanguageUtils.loadLocale;

public class CategoryActivity extends AppCompatActivity {


    private ActivityAddCategoryBinding binding;
    private static final String TAG = "Add Category";
    private CategoryViewModel categoryViewModel;
    private CategoryAdapter categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_category);


        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        setUpRecyclerView();

        getCategories();
    }



    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemlist.setLayoutManager(layoutManager);
        binding.itemlist.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.itemlist.addItemDecoration(dividerItemDecoration);
    }

    private void getCategories() {
        categoryViewModel.getCategory().observe(this, CategoryResponse -> {
            categoryAdapter = new CategoryAdapter(getApplicationContext(), CategoryResponse.getCategory());
            binding.itemlist.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
        });
    }





}