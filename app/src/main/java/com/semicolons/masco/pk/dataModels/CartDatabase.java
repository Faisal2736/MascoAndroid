package com.semicolons.masco.pk.dataModels;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CartDataTable.class,FavDataTable.class},version = 2)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDao cartDao();
    public abstract FavDao favDao();

    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context){

        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),CartDatabase.class,"cart_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }

        return instance;
    }
}
