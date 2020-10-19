package com.shopping_point.vendor_shopping_point.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.shopping_point.vendor_shopping_point.repository.DeleteUserRepository;

import okhttp3.ResponseBody;

public class DeleteUserViewModel extends AndroidViewModel {

    private DeleteUserRepository deleteUserRepository;

    public DeleteUserViewModel(@NonNull Application application) {
        super(application);
        deleteUserRepository = new DeleteUserRepository(application);
    }

    public LiveData<ResponseBody> deleteUser(int userId) {
        return deleteUserRepository.deleteUser(userId);
    }
}

