package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Points implements Serializable {

    @SerializedName("User Total Points")
    private String TotalPoints;
    @SerializedName("Total Discount")
    private String TotalDiscount;

    private String message;
    private int status;

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

    public String getTotalPoints() {
        return TotalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        TotalPoints = totalPoints;
    }

    public String getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        TotalDiscount = totalDiscount;
    }
}
