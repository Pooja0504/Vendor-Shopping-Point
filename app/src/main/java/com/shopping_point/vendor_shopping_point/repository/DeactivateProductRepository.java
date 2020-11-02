package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeactivateProductRepository {

    private static final String TAG = DeactivateProductRepository.class.getSimpleName();
    private Application application;

    public DeactivateProductRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> deactivateProduct(int product_id) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().deactivateProduct(product_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                Log.d(TAG, "onResponse Product Activation : Succeeded");


                ResponseBody responseBody = response.body();
                   // Toast.makeText(application, "Response : "+responseBody, Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                  //  Toast.makeText(application, "Account Activated Successfully", Toast.LENGTH_SHORT).show();
                    mutableLiveData.setValue(responseBody);
                }
            }else{
                   // Toast.makeText(application, "Faaaiiiillllleedd", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure Product Activation : " + t.getMessage());
            }
        });

        return mutableLiveData;
    }

}
