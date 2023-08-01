package com.example.exelab7chat.api;

import com.example.exelab7chat.model.DataChat;
import com.example.exelab7chat.model.MessageChat;
import com.example.exelab7chat.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequest {
    @POST("auth")
    Call<User> loginAndRegister(@Body User user);

    @GET("getUser/{id}")
    Call<List<User>> getListUser(@Path("id") String id);

    @POST("addmsg/")
    Call<MessageChat> addMessage(@Body MessageChat message);

    @GET("getmsg/{send}&{sendTo}")
    Call<DataChat> getDataChat(@Path("send") String sendId, @Path("sendTo") String sendToId);
}
