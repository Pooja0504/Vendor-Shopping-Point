package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.model.GetBannersApiResponse;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBannersRepository {

    private static final String TAG = GetBannersRepository.class.getSimpleName();
    private Application application;

    public GetBannersRepository(Application application) {
        this.application = application;
    }

    public LiveData<GetBannersApiResponse> getPosters1() {
        final MutableLiveData<GetBannersApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getBanners().enqueue(new Callback<GetBannersApiResponse>() {
            @Override
            public void onResponse(Call<GetBannersApiResponse> call, Response<GetBannersApiResponse> response) {

                Log.d("onResponse", "" + response.code());

                GetBannersApiResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<GetBannersApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(application, "POSTER : FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
