package com.semicolons.masco.pk.dataModels;

import java.util.ArrayList;

public class SliderImagesResponse {

    private int status;
    private String message;
    private ArrayList<SliderImages> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SliderImages> getData() {
        return data;
    }

    public void setData(ArrayList<SliderImages> data) {
        this.data = data;
    }
}
