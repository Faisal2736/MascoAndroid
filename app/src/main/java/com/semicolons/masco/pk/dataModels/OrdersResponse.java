package com.semicolons.masco.pk.dataModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrdersResponse {

	@SerializedName("order detail")
	private List<Order> orderDetail;

	private String message;

	@SerializedName("order total")
	private float orderTotal;

	public void setOrderDetail(List<Order> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public float getDevelory() {
		return develory;
	}

	public void setDevelory(float develory) {
		this.develory = develory;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public void setPoint(int point) {
		Point = point;
	}

	private float develory;

	private int discount;
	private int Point;

	public float getOrderTotal() {
		return orderTotal;
	}

	public int getDiscount() {
		return discount;
	}

	public int getPoint() {
		return Point;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Order> getOrderDetail(){
		return orderDetail;
	}


}