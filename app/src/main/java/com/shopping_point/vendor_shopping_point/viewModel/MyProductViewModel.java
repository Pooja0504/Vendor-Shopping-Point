package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.MyProductResponse;
import com.shopping_point.vendor_shopping_point.repository.MyProductRepository;


public class MyProductViewModel extends AndroidViewModel {

    private MyProductRepository myProductRepository;

    public MyProductViewModel(@NonNull Application application) {
        super(application);
        myProductRepository = new MyProductRepository(application);
    }

    public LiveData<MyProductResponse> getMyproduct() {
        return myProductRepository.getMyproduct();
    }
}
