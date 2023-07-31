package com.example.exelab7chat.model;

import java.util.List;

public class Data {
    private List<Content> data;

    public Data(List<Content> data) {
        this.data = data;
    }

    public List<Content> getData() {
        return data;
    }

    public void setData(List<Content> data) {
        this.data = data;
    }
}
