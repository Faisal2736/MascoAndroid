package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class AddFavResponse {

	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}