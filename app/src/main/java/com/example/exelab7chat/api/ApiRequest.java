package com.example.exelab7chat.api;

import com.example.exelab7chat.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @POST("auth")
    Call<User> loginAndRegister(@Body User user);

    @GET("getUser")
    Call<List<User>> getListUser();
}
