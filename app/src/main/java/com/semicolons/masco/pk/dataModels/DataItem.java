package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItem implements Serializable {

	@SerializedName("category name")
	private String categoryName;

	@SerializedName("category ID")
	private int categoryID;

	@SerializedName("slug")
	private String slug;

	public String getNo_of_products() {
		return no_of_products;
	}

	public void setNo_of_products(String no_of_products) {
		this.no_of_products = no_of_products;
	}

	private String no_of_products;

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@SerializedName("thumbnail")
	private String thumbnail;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	@Override
	public String toString() {
		return
				"DataItem{" +
						"category name = '" + categoryName + '\'' +
						",category ID = '" + categoryID + '\'' +
						",slug = '" + slug + '\'' +
						"}";
	}
}