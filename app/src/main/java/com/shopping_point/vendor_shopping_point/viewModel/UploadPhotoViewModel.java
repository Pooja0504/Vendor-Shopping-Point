package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shopping_point.vendor_shopping_point.repository.UploadPhotoRepository;

import okhttp3.ResponseBody;

public class UploadPhotoViewModel extends AndroidViewModel {

    private UploadPhotoRepository uploadPhotoRepository;

    public UploadPhotoViewModel(@NonNull Application application) {
        super(application);
        uploadPhotoRepository = new UploadPhotoRepository(application);
    }

    public LiveData<ResponseBody> uploadPhoto(String pathname) {
        return uploadPhotoRepository.uploadPhoto(pathname);
    }
}
