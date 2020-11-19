package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.model.NewsFeedResponse1;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedRepository1 {

    private static final String TAG = NewsFeedRepository1.class.getSimpleName();
    private Application application;

    public NewsFeedRepository1(Application application) {
        this.application = application;
    }

    public LiveData<NewsFeedResponse1> getPosters1() {
        final MutableLiveData<NewsFeedResponse1> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getPosters1().enqueue(new Callback<NewsFeedResponse1>() {
            @Override
            public void onResponse(Call<NewsFeedResponse1> call, Response<NewsFeedResponse1> response) {

                Log.d("onResponse", "" + response.code());

                NewsFeedResponse1 responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<NewsFeedResponse1> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(application, "POSTER : FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
