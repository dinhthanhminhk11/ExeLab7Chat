package com.example.exelab7chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.exelab7chat.model.User;
import com.example.exelab7chat.repository.Repository;

public class LoginViewModel extends AndroidViewModel {
    private Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public LiveData<User> loginAndRegister(User user) {
        return repository.loginAndRegister(user);
    }
}
