package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class DeleteFavResponse {

	@SerializedName("data")
	private String data;

	public String getData(){
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}