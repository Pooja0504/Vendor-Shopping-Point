package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.RegisterApiResponse;
import com.shopping_point.vendor_shopping_point.model.Vendor;
import com.shopping_point.vendor_shopping_point.repository.RegisterRepository;


public class RegisterViewModel extends AndroidViewModel {

    private RegisterRepository registerRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        registerRepository = new RegisterRepository(application);
    }


    public LiveData<RegisterApiResponse> getRegisterResponseLiveData(Vendor vendor) {
        return registerRepository.getRegisterResponseData(vendor);
    }
}
