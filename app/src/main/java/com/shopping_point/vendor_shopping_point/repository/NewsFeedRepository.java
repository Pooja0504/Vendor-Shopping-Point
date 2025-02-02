package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.model.NewsFeedResponse;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedRepository {

    private static final String TAG = NewsFeedRepository.class.getSimpleName();
    private Application application;

    public NewsFeedRepository(Application application) {
        this.application = application;
    }

    public LiveData<NewsFeedResponse> getPosters() {
        final MutableLiveData<NewsFeedResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getPosters().enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(Call<NewsFeedResponse> call, Response<NewsFeedResponse> response) {

                Log.d("onResponse", "" + response.code());

                NewsFeedResponse responseBody = response.body();
               // Toast.makeText(application, response.body() + " ", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<NewsFeedResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(application, "POSTER : FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

        return mutableLiveData;
    }
}
