package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.NewsFeedResponse1;
import com.shopping_point.vendor_shopping_point.repository.NewsFeedRepository1;


public class NewsFeedViewModel1 extends AndroidViewModel {

    private NewsFeedRepository1 newsFeedRepository;

    public NewsFeedViewModel1(@NonNull Application application) {
        super(application);
        newsFeedRepository = new NewsFeedRepository1(application);
    }

    public LiveData<NewsFeedResponse1> getPosters1() {
        return newsFeedRepository.getPosters1();
    }
}
