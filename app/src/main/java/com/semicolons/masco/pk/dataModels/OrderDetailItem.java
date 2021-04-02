package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class OrderDetailItem{

	@SerializedName("total")
	private String total;

	@SerializedName("Product title")
	private String productTitle;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("Discount")
	private int discount;

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setProductTitle(String productTitle){
		this.productTitle = productTitle;
	}

	public String getProductTitle(){
		return productTitle;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setDiscount(int discount){
		this.discount = discount;
	}

	public int getDiscount(){
		return discount;
	}

	@Override
	public String toString(){
		return
				"OrderDetailItem{" +
						"total = '" + total + '\'' +
						",product title = '" + productTitle + '\'' +
						",quantity = '" + quantity + '\'' +
						",discount = '" + discount + '\'' +
						"}";
	}
}