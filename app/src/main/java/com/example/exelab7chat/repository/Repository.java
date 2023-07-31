package com.example.exelab7chat.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.exelab7chat.api.ApiRequest;
import com.example.exelab7chat.model.Content;
import com.example.exelab7chat.model.User;
import com.example.exelab7chat.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ApiRequest apiRequest;

    public Repository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<User> loginAndRegister(User user) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        apiRequest.loginAndRegister(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzz", t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<List<User>> getHost() {
        final MutableLiveData<List<User>> user = new MutableLiveData<>();
        apiRequest.getListUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    user.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzz", t.getMessage() + "error");
            }
        });
        return user;
    }


}
