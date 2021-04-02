package com.semicolons.masco.pk.dataModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartDataTable {

    @PrimaryKey(autoGenerate = true)
    private int cartId;

    @ColumnInfo(name = "product_id")
    private int productId;

    @ColumnInfo(name = "product_quantity")
    private int productQuantity;

    @ColumnInfo(name = "user_id")
    private int userId;

    @Ignore
    public CartDataTable(){

    }

    public CartDataTable(int cartId, int productId, int productQuantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
