package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shopping_point.vendor_shopping_point.model.OrderApiResponse;
import com.shopping_point.vendor_shopping_point.repository.OrdersRepository;

public class OrdersViewModel extends AndroidViewModel {


    private OrdersRepository ordersRepository;

    public OrdersViewModel(@NonNull Application application) {
        super(application);
        ordersRepository = new OrdersRepository(application);
    }

    public LiveData<OrderApiResponse> getOrdersDetails(int seller_id) {
        return ordersRepository.getOrdersDetails(seller_id);
    }
}
