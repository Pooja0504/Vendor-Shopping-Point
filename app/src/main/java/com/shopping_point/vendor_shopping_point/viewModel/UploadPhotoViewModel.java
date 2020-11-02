package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.UploadPhoto;
import com.shopping_point.vendor_shopping_point.model.UploadPhotoApiResponse;
import com.shopping_point.vendor_shopping_point.repository.UploadPhotoRepository;


public class UploadPhotoViewModel extends AndroidViewModel {

    private UploadPhotoRepository uploadPhotoRepository;



    public UploadPhotoViewModel(@NonNull Application application) {
        super(application);
        uploadPhotoRepository = new UploadPhotoRepository(application);
    }


    public LiveData<UploadPhotoApiResponse> getUploadPhotoResponseLiveData(UploadPhoto uploadPhoto) {
        return uploadPhotoRepository.getUploadPhotoResponseData(uploadPhoto);
    }
}
