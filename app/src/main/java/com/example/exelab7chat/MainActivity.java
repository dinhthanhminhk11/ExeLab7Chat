package com.example.exelab7chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.exelab7chat.constants.AppConstant;
import com.example.exelab7chat.databinding.ActivityMainBinding;
import com.example.exelab7chat.model.User;
import com.example.exelab7chat.model.UserClient;
import com.example.exelab7chat.ui.activity.ChatActivity;
import com.example.exelab7chat.ui.adapter.HostAdapter;
import com.example.exelab7chat.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HostAdapter.EventClick {
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

        mainViewModel.getHost(UserClient.getInstance().get_id()).observe(this, it -> {
            hostAdapter.setListHost(it);
            hostAdapter.setEventClick(this);
            binding.rcvListHost.setAdapter(hostAdapter);
        });
    }

    @Override
    public void onClick(User user) {
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra(AppConstant.ID_USER, user.get_id());
        intent.putExtra(AppConstant.NAME_USER, user.getName());
        startActivity(intent);
    }
}