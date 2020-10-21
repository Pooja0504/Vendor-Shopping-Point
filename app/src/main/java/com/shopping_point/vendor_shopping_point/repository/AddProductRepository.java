package com.shopping_point.vendor_shopping_point.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shopping_point.vendor_shopping_point.model.AddProductApiResponse;
import com.shopping_point.vendor_shopping_point.model.Product;
import com.shopping_point.vendor_shopping_point.model.RegisterApiResponse;
import com.shopping_point.vendor_shopping_point.model.Vendor;
import com.shopping_point.vendor_shopping_point.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;


public class AddProductRepository {

    private static final String TAG = AddProductRepository.class.getSimpleName();
    private Application application;

    public AddProductRepository(Application application) {
        this.application = application;
    }


    public LiveData<AddProductApiResponse> getAddProductResponseData(Product product) {
        final MutableLiveData<AddProductApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().createPendor(product).enqueue(new Callback<AddProductApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AddProductApiResponse> call, Response<AddProductApiResponse> response) {

                Toast.makeText(application, response.message(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onResponse: Succeeded" + response.body().getMessage());

                AddProductApiResponse addProductApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(addProductApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AddProductApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
