package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.Image;
import com.shopping_point.vendor_shopping_point.repository.VendorImageRepository;


public class VendorImageViewModel extends AndroidViewModel {

    private VendorImageRepository vendorImageRepository;

    public VendorImageViewModel(@NonNull Application application) {
        super(application);
        vendorImageRepository = new VendorImageRepository(application);
    }

    public LiveData<Image> getVendorImage(int userId) {
        return vendorImageRepository.getUserImage(userId);
    }
}
