package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.model.MyProductResponse;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProductRepository {

    private static final String TAG = MyProductRepository.class.getSimpleName();
    private Application application;

    public MyProductRepository(Application application) {
        this.application = application;
    }

    public LiveData<MyProductResponse> getMyProduct() {
        final MutableLiveData<MyProductResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getMyproduct().enqueue(new Callback<MyProductResponse>() {
            @Override
            public void onResponse(Call<MyProductResponse> call, Response<MyProductResponse> response) {

                Log.d("onResponse", "" + response.code());

                MyProductResponse responseBody = response.body();
                Toast.makeText(application, response.body() + "Vendor Repo ", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<MyProductResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(application, "VENDOR : FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
