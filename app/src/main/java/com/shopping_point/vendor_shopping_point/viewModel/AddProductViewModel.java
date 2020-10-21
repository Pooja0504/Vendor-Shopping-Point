package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.AddProductApiResponse;
import com.shopping_point.vendor_shopping_point.model.RegisterApiResponse;
import com.shopping_point.vendor_shopping_point.model.Vendor;
import com.shopping_point.vendor_shopping_point.repository.AddProductRepository;
import com.shopping_point.vendor_shopping_point.repository.RegisterRepository;


public class AddProductViewModel extends AndroidViewModel {

    private AddProductRepository addProductRepository;

    public AddProductViewModel(@NonNull Application application) {
        super(application);
        addProductRepository = new AddProductRepository(application);
    }


    public LiveData<AddProductApiResponse> getAddProductResponseLiveData(Vendor vendor) {
        return addProductRepository.getAddProductResponseData(vendor);
    }
}
