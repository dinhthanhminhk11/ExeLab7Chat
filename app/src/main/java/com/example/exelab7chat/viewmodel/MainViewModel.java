package com.example.exelab7chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.exelab7chat.model.Content;
import com.example.exelab7chat.model.User;
import com.example.exelab7chat.repository.Repository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public LiveData<List<User>> getHost() {
        return repository.getHost();
    }

    public LiveData<List<Content>> getMsgId(String send) {
        return repository.getMsgId(send);
    }
}
