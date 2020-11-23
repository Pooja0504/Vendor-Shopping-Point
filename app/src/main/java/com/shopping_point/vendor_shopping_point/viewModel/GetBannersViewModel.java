package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.GetBannersApiResponse;
import com.shopping_point.vendor_shopping_point.repository.GetBannersRepository;


public class GetBannersViewModel extends AndroidViewModel {

    private GetBannersRepository newsFeedRepository;

    public GetBannersViewModel(@NonNull Application application) {
        super(application);
        newsFeedRepository = new GetBannersRepository(application);
    }

    public LiveData<GetBannersApiResponse> getPosters1() {
        return newsFeedRepository.getPosters1();
    }
}
