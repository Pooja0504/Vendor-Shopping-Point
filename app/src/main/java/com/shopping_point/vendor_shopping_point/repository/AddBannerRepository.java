package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.model.AddBannerApiResponse;
import com.shopping_point.vendor_shopping_point.model.Banner;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;


public class AddBannerRepository {

    private static final String TAG = AddBannerRepository.class.getSimpleName();
    private Application application;

    public AddBannerRepository(Application application) {
        this.application = application;
    }


    public LiveData<AddBannerApiResponse> getAddBannerResponseData(Banner banner) {
        final MutableLiveData<AddBannerApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().createBanner(banner).enqueue(new Callback<AddBannerApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddBannerApiResponse> call, Response<AddBannerApiResponse> response) {

                //Toast.makeText(application, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                AddBannerApiResponse addBannerApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(addBannerApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AddBannerApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
