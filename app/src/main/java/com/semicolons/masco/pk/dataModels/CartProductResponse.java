package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartProductResponse {

	@SerializedName("data")
	private List<CartProduct> data;

	@SerializedName("discount(%10)")
	private String discount;

	@SerializedName("Total")
	private String total;

	@SerializedName("Sub Price")
	private String subPrice;

	@SerializedName("points")
	private int points;

	//private String message;

	/*public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}*/

	public List<CartProduct> getData(){
		return data;
	}

	public String getDiscount10(){
		return discount;
	}

	public String getTotal(){
		return total;
	}

	public String getSubPrice(){
		return subPrice;
	}

	public int getPoints(){
		return points;
	}

	public void setData(List<CartProduct> data) {
		this.data = data;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setSubPrice(String subPrice) {
		this.subPrice = subPrice;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}