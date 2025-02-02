package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.model.Otp;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpRepository {

    private static final String TAG = OtpRepository.class.getSimpleName();
    private Application application;

    public OtpRepository(Application application) {
        this.application = application;
    }

    public LiveData<Otp> getOtpCode(String email) {
        final MutableLiveData<Otp> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getOtp(email).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {

                Log.d(TAG, "onResponse: Succeeded");

                Otp otp;
                if (response.code() == 200) {
                    otp = response.body();
                } else {
                    otp = new Otp("Incorrect Email");
                }
                mutableLiveData.setValue(otp);

            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
