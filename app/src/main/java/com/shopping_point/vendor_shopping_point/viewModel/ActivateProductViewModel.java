package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shopping_point.vendor_shopping_point.repository.ActivateProductRepository;

import okhttp3.ResponseBody;

public class ActivateProductViewModel extends AndroidViewModel {

    private ActivateProductRepository activateProductRepository;

    public ActivateProductViewModel(@NonNull Application application) {
        super(application);
        activateProductRepository = new ActivateProductRepository(application);
    }

    public LiveData<ResponseBody> activateVendor(int product_id) {
        return activateProductRepository.activateProduct(product_id);
    }
}

