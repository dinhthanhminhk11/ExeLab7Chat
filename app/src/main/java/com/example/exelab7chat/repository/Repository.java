package com.example.exelab7chat.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.exelab7chat.api.ApiRequest;
import com.example.exelab7chat.constants.AppConstant;
import com.example.exelab7chat.model.Content;
import com.example.exelab7chat.model.ContentChat;
import com.example.exelab7chat.model.DataChat;
import com.example.exelab7chat.model.MessageChat;
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

    public MutableLiveData<List<User>> getHost(String id) {
        final MutableLiveData<List<User>> user = new MutableLiveData<>();
        apiRequest.getListUser(id).enqueue(new Callback<List<User>>() {
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

    public void insertMessage(MessageChat message) {
        apiRequest.addMessage(message).enqueue(new Callback<MessageChat>() {
            @Override
            public void onResponse(Call<MessageChat> call, Response<MessageChat> response) {
                Log.e(AppConstant.CALL_ERROR, "da gui");
            }

            @Override
            public void onFailure(Call<MessageChat> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, t.getMessage());
            }
        });
    }

    public MutableLiveData<List<ContentChat>> getContentChat(String sendId, String sendToId) {
        final MutableLiveData<List<ContentChat>> data = new MutableLiveData<>();
        apiRequest.getDataChat(sendId, sendToId).enqueue(new Callback<DataChat>() {
            @Override
            public void onResponse(Call<DataChat> call, Response<DataChat> response) {
                data.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<DataChat> call, Throwable t) {
                Log.e(AppConstant.CALL_ERROR, t.getMessage());
            }
        });
        return data;
    }

}
