package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.AddBannerApiResponse;
import com.shopping_point.vendor_shopping_point.model.Banner;
import com.shopping_point.vendor_shopping_point.repository.AddBannerRepository;


public class AddBannerViewModel extends AndroidViewModel {

    private AddBannerRepository addBannerRepository;

    public AddBannerViewModel(@NonNull Application application) {
        super(application);
        addBannerRepository = new AddBannerRepository(application);
    }


    public LiveData<AddBannerApiResponse> getAddBannerResponseLiveData(Banner banner) {
        return addBannerRepository.getAddBannerResponseData(banner);
    }
}
