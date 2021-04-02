package com.semicolons.masco.pk.dataModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_table")
public class FavDataTable {

    @PrimaryKey(autoGenerate = true)
    private int favId;
    @ColumnInfo(name = "product_id")
    private int productId;
    @ColumnInfo(name = "user_id")
    private int userId;

    public FavDataTable() {
    }

    public FavDataTable(int favId, int productId, int userId) {
        this.favId = favId;
        this.productId = productId;
        this.userId = userId;
    }

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
