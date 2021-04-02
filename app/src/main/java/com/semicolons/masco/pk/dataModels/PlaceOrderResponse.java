package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class PlaceOrderResponse {

	@SerializedName("data")
	private String data;

	@SerializedName("order_id")
	private int orderId;

	public String getData(){
		return data;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setData(String data) {
		this.data = data;
	}
}