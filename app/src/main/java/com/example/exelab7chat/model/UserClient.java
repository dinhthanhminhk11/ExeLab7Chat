package com.example.exelab7chat.model;

public class UserClient {
    private String _id;
    private String name;
    private String email;
    private static UserClient instance = null;

    protected UserClient() {
    }

    public static UserClient getInstance() {
        if (instance == null) {
            instance = new UserClient();
        }
        return instance;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
