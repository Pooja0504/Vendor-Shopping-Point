package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shopping_point.vendor_shopping_point.repository.DeactivateProductRepository;

import okhttp3.ResponseBody;

public class DeactivateProductViewModel extends AndroidViewModel {

    private DeactivateProductRepository deactivateProductRepository;

    public DeactivateProductViewModel(@NonNull Application application) {
        super(application);
        deactivateProductRepository = new DeactivateProductRepository(application);
    }

    public LiveData<ResponseBody> deactivateVendor(int product_id) {
        return deactivateProductRepository.deactivateProduct(product_id);
    }
}

