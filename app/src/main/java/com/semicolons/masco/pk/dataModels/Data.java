package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("address")
    private String address;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("name")
    private String name;

    @SerializedName("mobile_no")
    private String mobileNo;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "user_email = '" + userEmail + '\'' +
                        ",address = '" + address + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",user_name = '" + userName + '\'' +
                        ",company_name = '" + companyName + '\'' +
                        ",name = '" + name + '\'' +
                        ",mobile_no = '" + mobileNo + '\'' +
                        "}";
    }
}