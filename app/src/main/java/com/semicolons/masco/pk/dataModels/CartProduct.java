package com.semicolons.masco.pk.dataModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartProduct implements Serializable {

    @SerializedName("image_name")
    private String imageName;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("product_title")
    private String productTitle;

    @SerializedName("Unit price")
    private String unitPrice;

    public String getImageName(){
        return imageName;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getProductId(){
        return productId;
    }

    public String getProductTitle(){
        return productTitle;
    }

    public String getUnitPrice(){
        return unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
