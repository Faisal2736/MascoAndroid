package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class ProfileUpdateResponse {

	@SerializedName("data")
	private Profile data;

	@SerializedName("Success")
	private String success;

	@SerializedName("status")
	private int status;

	public void setData(Profile data) {
		this.data = data;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Profile getData(){
		return data;
	}

	public String getSuccess(){
		return success;
	}

	public int getStatus(){
		return status;
	}
}