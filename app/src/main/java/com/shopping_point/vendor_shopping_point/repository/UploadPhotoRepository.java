package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.net.RetrofitClient;


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

    public LiveData<ResponseBody> uploadPhoto(String image,int id) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().uploadPhoto(image,id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse Upload User : without checking response");
              //  Toast.makeText(application, response.body().toString(), Toast.LENGTH_SHORT).show();
                ResponseBody responseBody = response.body();
                if (response.body() != null) {
                    Log.d(TAG, "onResponse Upload User image: Succeeded");
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure Upload User image : " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
