package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDM {

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("message")
    private String message;
    private int status;

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return
                "CategoryDM{" +
                        "data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}