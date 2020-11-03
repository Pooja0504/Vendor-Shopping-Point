package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.NotificationResponse;
import com.shopping_point.vendor_shopping_point.repository.NotificationRepository;
import com.shopping_point.vendor_shopping_point.storage.LoginUtils;


public class NotificationViewModel extends AndroidViewModel {

    private NotificationRepository notificationRepository;

    public NotificationViewModel(@NonNull Application application) {
        super(application);

        notificationRepository = new NotificationRepository(application);
    }

    public LiveData<NotificationResponse> getNotification(int seller_id) {
        return notificationRepository.getNotification(seller_id);
    }
}
