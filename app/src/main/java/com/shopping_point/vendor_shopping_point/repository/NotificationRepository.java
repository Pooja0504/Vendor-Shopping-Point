package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.model.NewsFeedResponse;
import com.shopping_point.vendor_shopping_point.model.NotificationResponse;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {

    private static final String TAG = NotificationRepository.class.getSimpleName();
    private Application application;

    public NotificationRepository(Application application) {
        this.application = application;
    }
    int seller_id = LoginUtils.getInstance(application).getVendorInfo().getId();
    public LiveData<NotificationResponse> getNotification(int seller_id) {
        final MutableLiveData<NotificationResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getNotification(this.seller_id).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {

                Log.d("onResponse", "" + response.code());

                NotificationResponse responseBody = response.body();
                // Toast.makeText(application, response.body() + " ", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(application, "POSTER : FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
