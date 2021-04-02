package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class Profile {

	@SerializedName("user_email")
	private String userEmail;

	@SerializedName("address")
	private String address;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("name")
	private String name;

	private String password;

	@SerializedName("mobile_no")
	private String mobileNo;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserEmail(){
		return userEmail;
	}

	public String getAddress(){
		return address;
	}

	public String getUserName(){
		return userName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public String getName(){
		return name;
	}

	public String getMobileNo(){
		return mobileNo;
	}
}