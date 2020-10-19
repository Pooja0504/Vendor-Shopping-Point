package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shopping_point.vendor_shopping_point.repository.FromHistoryRepository;

import okhttp3.ResponseBody;

public class FromHistoryViewModel extends AndroidViewModel {

    private FromHistoryRepository fromHistoryRepository;

    public FromHistoryViewModel(@NonNull Application application) {
        super(application);
        fromHistoryRepository = new FromHistoryRepository(application);
    }

    public LiveData<ResponseBody> removeAllFromHistory() {
        return fromHistoryRepository.removeAllFromHistory();
    }
}
