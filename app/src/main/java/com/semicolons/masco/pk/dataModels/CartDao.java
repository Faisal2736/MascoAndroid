package com.semicolons.masco.pk.dataModels;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(CartDataTable cartDataTable);

    @Update
    void update(CartDataTable cartDataTable);

    @Delete
    void delete(CartDataTable cartDataTable);

    @Query("delete from cart_table")
    void deleteAllCart();

    @Query("select * from cart_table where user_id=:userId")
    LiveData<List<CartDataTable>> getCartsList(int userId);

    @Query("select product_id from cart_table where user_id=:userId")
    LiveData<List<Integer>> getProductIds(int userId);

    @Query("select Count(DISTINCT product_id) from cart_table where user_id=:userId")
    LiveData<Integer> getCartCount(int userId);

    @Query("select * from cart_table where product_id==:productId and  user_id=:userId")
    LiveData<CartDataTable> getCart(int productId, int userId);

    @Query("select * from cart_table where product_id==:productId and user_id=:userId")
    CartDataTable getCart2(int productId, int userId);

    @Query("update Cart_Table set product_quantity=:productQuantity  where  user_id=:userId and product_id=:productId ")
    void cartforUpdate(int productQuantity, int userId, int productId);

}
