package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shopping_point.vendor_shopping_point.repository.PasswordRepository;

import okhttp3.ResponseBody;

public class PasswordViewModel extends AndroidViewModel {

    private PasswordRepository passwordRepository;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = new PasswordRepository(application);
    }

    public LiveData<ResponseBody> updatePassword(String newPassword, String email) {
        return passwordRepository.updatePassword(newPassword,email);
    }

}
