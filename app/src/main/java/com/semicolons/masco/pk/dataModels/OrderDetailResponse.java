package com.semicolons.masco.pk.dataModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderDetailResponse{

	@SerializedName("order detail")
	private List<OrderDetailItem> orderDetail;

	@SerializedName("discount")
	private String discount;

	@SerializedName("order total")
	private String orderTotal;
	private String develory;

	public String getDevelory() {
		return develory;
	}

	public void setDevelory(String develory) {
		this.develory = develory;
	}

	@SerializedName("Point")
	private String point;

	public void setOrderDetail(List<OrderDetailItem> orderDetail){
		this.orderDetail = orderDetail;
	}

	public List<OrderDetailItem> getOrderDetail(){
		return orderDetail;
	}

	public void setDiscount(String discount){
		this.discount = discount;
	}

	public String getDiscount(){
		return discount;
	}

	public void setOrderTotal(String orderTotal){
		this.orderTotal = orderTotal;
	}

	public String getOrderTotal(){
		return orderTotal;
	}

	public void setPoint(String point){
		this.point = point;
	}

	public String getPoint(){
		return point;
	}

	@Override
	public String toString(){
		return
				"TestHammad{" +
						"order detail = '" + orderDetail + '\'' +
						",discount = '" + discount + '\'' +
						",order total = '" + orderTotal + '\'' +
						",point = '" + point + '\'' +
						"}";
	}
}