package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.net.RetrofitClient;
import com.shopping_point.vendor_shopping_point.utils.RequestCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemovePosterRepository {

    private static final String TAG = RemovePosterRepository.class.getSimpleName();
    private Application application;

    public RemovePosterRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> removePoster(int posterId, RequestCallback callback) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().removePoster(posterId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("onResponse", "" + response.code());

                if(response.code() == 200){
                    callback.onCallBack();
                }

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", "" + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
