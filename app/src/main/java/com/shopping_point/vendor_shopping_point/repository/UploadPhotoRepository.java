package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;




import com.shopping_point.vendor_shopping_point.model.UploadPhoto;
import com.shopping_point.vendor_shopping_point.model.UploadPhotoApiResponse;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;


public class UploadPhotoRepository {

    private static final String TAG = AddBannerRepository.class.getSimpleName();
    private Application application;

    public UploadPhotoRepository(Application application) {
        this.application = application;
    }


    public LiveData<UploadPhotoApiResponse> getUploadPhotoResponseData(UploadPhoto uploadPhoto) {
        final MutableLiveData<UploadPhotoApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().uploadPhoto(uploadPhoto).enqueue(new Callback<UploadPhotoApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UploadPhotoApiResponse> call, Response<UploadPhotoApiResponse> response) {

                //Toast.makeText(application, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                UploadPhotoApiResponse uploadPhotoApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(uploadPhotoApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UploadPhotoApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
