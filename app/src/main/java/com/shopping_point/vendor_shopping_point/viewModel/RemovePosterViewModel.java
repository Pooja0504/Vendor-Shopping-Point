package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shopping_point.vendor_shopping_point.repository.RemovePosterRepository;
import com.shopping_point.vendor_shopping_point.utils.RequestCallback;

import okhttp3.ResponseBody;

public class RemovePosterViewModel extends AndroidViewModel {

    private RemovePosterRepository removeFavoriteRepository;

    public RemovePosterViewModel(@NonNull Application application) {
        super(application);
        removeFavoriteRepository = new RemovePosterRepository(application);
    }

    public LiveData<ResponseBody> removePoster(int posterId, RequestCallback callback) {
        return removeFavoriteRepository.removePoster(posterId, callback);
    }
}
