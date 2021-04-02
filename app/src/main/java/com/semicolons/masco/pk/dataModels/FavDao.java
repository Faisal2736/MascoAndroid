package com.semicolons.masco.pk.dataModels;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavDao {

    @Insert
    void insert(FavDataTable favDataTable);

    @Update
    void update(FavDataTable favDataTable);

    @Delete
    void delete(FavDataTable favDataTable);

    @Query("Select * from fav_table where user_id=:userId")
    LiveData<List<FavDataTable>> getAllFavourites(int userId);

    @Query("Select * from fav_table where product_id=:productId and user_id=:userId")
    FavDataTable getFavourite(int productId,int userId);

}
