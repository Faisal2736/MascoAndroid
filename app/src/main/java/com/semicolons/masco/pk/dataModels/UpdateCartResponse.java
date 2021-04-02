package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateCartResponse {

	@SerializedName("data")
	private List<CartProduct> data;

	@SerializedName("Total Price")
	private String totalAmount;

	@SerializedName("Message")
	private String message;

    @SerializedName("discount")
	private String discount;

	@SerializedName("Grand Total")
    private String grandTotal;

	public List<CartProduct> getData() {
		return data;
	}

	public String getTotalAmount(){
		return totalAmount;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public String getDiscount() {
		return discount;
	}

	public String getGrandTotal(){
		return grandTotal;
    }
}