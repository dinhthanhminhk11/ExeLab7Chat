package com.example.exelab7chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.exelab7chat.model.User;
import com.example.exelab7chat.repository.Repository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private Repository repository;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }


}
