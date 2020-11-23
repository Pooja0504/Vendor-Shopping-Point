package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shopping_point.vendor_shopping_point.model.OrderApiResponse;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersRepository {

    private static final String TAG = OrdersRepository.class.getSimpleName();
    private Application application;

    public OrdersRepository(Application application) {
        this.application = application;
    }

    public LiveData<OrderApiResponse> getOrdersDetails(int seller_id) {
        final MutableLiveData<OrderApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getOrdersDetails(seller_id).enqueue(new Callback<OrderApiResponse>() {
            @Override
            public void onResponse(Call<OrderApiResponse> call, Response<OrderApiResponse> response) {

                Log.d("onResponse", "" + response.code());

                OrderApiResponse responseBody = response.body();
                //Toast.makeText(application, response.body() + "Vendor Repo ", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {

                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<OrderApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(application, "VENDOR : FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
