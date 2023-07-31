package com.example.exelab7chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.exelab7chat.databinding.ActivityMainBinding;
import com.example.exelab7chat.model.User;
import com.example.exelab7chat.viewmodel.LoginViewModel;
import com.example.exelab7chat.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<User> mListHost;
    private MainViewModel mainViewModel;
    private HostAdapter hostAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mListHost = new ArrayList<>();
        hostAdapter = new HostAdapter();
        binding.rcvListHost.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mainViewModel.getHost().observe(this, it -> {
            hostAdapter.setListHost(mListHost);
            hostAdapter.setEventClick(this);
            binding.rcvListHost.setAdapter(hostAdapter);
        });
    }
}