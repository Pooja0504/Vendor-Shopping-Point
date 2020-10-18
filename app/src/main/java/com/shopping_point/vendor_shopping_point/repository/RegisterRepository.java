package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shopping_point.vendor_shopping_point.model.RegisterApiResponse;

import com.shopping_point.vendor_shopping_point.model.Vendor;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;


public class RegisterRepository {

    private static final String TAG = RegisterRepository.class.getSimpleName();
    private Application application;

    public RegisterRepository(Application application) {
        this.application = application;
    }


    public LiveData<RegisterApiResponse> getRegisterResponseData(Vendor vendor) {
        final MutableLiveData<RegisterApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().createVendor(vendor).enqueue(new Callback<RegisterApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterApiResponse> call, Response<RegisterApiResponse> response) {

                Toast.makeText(application, response.message(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onResponse: Succeeded" + response.body().getMessage());

                RegisterApiResponse registerApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(registerApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RegisterApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
