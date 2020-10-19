package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.NewsFeedResponse;
import com.shopping_point.vendor_shopping_point.repository.NewsFeedRepository;


public class NewsFeedViewModel extends AndroidViewModel {

    private NewsFeedRepository newsFeedRepository;

    public NewsFeedViewModel(@NonNull Application application) {
        super(application);
        newsFeedRepository = new NewsFeedRepository(application);
    }

    public LiveData<NewsFeedResponse> getPosters() {
        return newsFeedRepository.getPosters();
    }
}
