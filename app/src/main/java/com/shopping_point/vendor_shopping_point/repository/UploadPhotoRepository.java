package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.net.RetrofitClient;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhotoRepository {

    private static final String TAG = UploadPhotoRepository.class.getSimpleName();
    private Application application;

    public UploadPhotoRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> uploadPhoto(String photo, int id) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().uploadPhoto(photo, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + "Image Updated");
                Toast.makeText(application, "SUCESS : IMAGE UPLOADED ", Toast.LENGTH_SHORT).show();
                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                    Toast.makeText(application, "IN RESPONSE BODY", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
               // Toast.makeText(application, "FAILED " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
