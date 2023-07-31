package com.example.exelab7chat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.exelab7chat.MainActivity;
import com.example.exelab7chat.R;
import com.example.exelab7chat.databinding.ActivityLoginBinding;
import com.example.exelab7chat.model.User;
import com.example.exelab7chat.model.UserClient;
import com.example.exelab7chat.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {
            loginViewModel.loginAndRegister(new User(binding.name.getText().toString(), binding.email.getText().toString())).observe(this, it -> {
                UserClient userClient = UserClient.getInstance();
                userClient.set_id(it.get_id());
                userClient.setName(it.getName());
                userClient.setEmail(it.getEmail());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            });
        });

    }
}