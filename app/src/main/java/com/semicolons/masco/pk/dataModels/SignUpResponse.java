package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("Success")
    private String success;

    @SerializedName("status")
    private String status;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "SignUpResponse{" +
                        "data = '" + data + '\'' +
                        ",success = '" + success + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}