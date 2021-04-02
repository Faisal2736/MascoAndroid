package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

public class Order {

	@SerializedName("Total Point Eanrned")
	private String totalPointEanrned;

	@SerializedName("city")
	private String city;

	@SerializedName("Address2")
	private String address2;

	@SerializedName("last_name")
	private Object lastName;

	@SerializedName("Address1")
	private String address1;

	@SerializedName("order_date")
	private String orderDate;

	@SerializedName("phone")
	private String phone;

	@SerializedName("Country")
	private String country;

	@SerializedName("order total")
	private String orderTotal;

	@SerializedName("company")
	private String company;

	@SerializedName("currency")
	private String currency;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	public String getTotalPointEanrned(){
		return totalPointEanrned;
	}

	public String getCity(){
		return city;
	}

	public String getAddress2(){
		return address2;
	}

	public Object getLastName(){
		return lastName;
	}

	public String getAddress1(){
		return address1;
	}

	public String getOrderDate(){
		return orderDate;
	}

	public String getPhone(){
		return phone;
	}

	public String getCountry(){
		return country;
	}

	public String getOrderTotal(){
		return orderTotal;
	}

	public String getCompany(){
		return company;
	}

	public String getCurrency(){
		return currency;
	}

	public int getOrderId(){
		return orderId;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}
}