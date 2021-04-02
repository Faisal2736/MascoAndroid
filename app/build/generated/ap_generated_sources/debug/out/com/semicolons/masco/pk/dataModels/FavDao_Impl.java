package com.semicolons.masco.pk.dataModels;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FavDao_Impl implements FavDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavDataTable> __insertionAdapterOfFavDataTable;

  private final EntityDeletionOrUpdateAdapter<FavDataTable> __deletionAdapterOfFavDataTable;

  private final EntityDeletionOrUpdateAdapter<FavDataTable> __updateAdapterOfFavDataTable;

  public FavDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavDataTable = new EntityInsertionAdapter<FavDataTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `fav_table` (`favId`,`product_id`,`user_id`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavDataTable value) {
        stmt.bindLong(1, value.getFavId());
        stmt.bindLong(2, value.getProductId());
        stmt.bindLong(3, value.getUserId());
      }
    };
    this.__deletionAdapterOfFavDataTable = new EntityDeletionOrUpdateAdapter<FavDataTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `fav_table` WHERE `favId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavDataTable value) {
        stmt.bindLong(1, value.getFavId());
      }
    };
    this.__updateAdapterOfFavDataTable = new EntityDeletionOrUpdateAdapter<FavDataTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `fav_table` SET `favId` = ?,`product_id` = ?,`user_id` = ? WHERE `favId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavDataTable value) {
        stmt.bindLong(1, value.getFavId());
        stmt.bindLong(2, value.getProductId());
        stmt.bindLong(3, value.getUserId());
        stmt.bindLong(4, value.getFavId());
      }
    };
  }

  @Override
  public void insert(final FavDataTable favDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFavDataTable.insert(favDataTable);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final FavDataTable favDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFavDataTable.handle(favDataTable);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final FavDataTable favDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFavDataTable.handle(favDataTable);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<FavDataTable>> getAllFavourites(final int userId) {
    final String _sql = "Select * from fav_table where user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"fav_table"}, false, new Callable<List<FavDataTable>>() {
      @Override
      public List<FavDataTable> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFavId = CursorUtil.getColumnIndexOrThrow(_cursor, "favId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final List<FavDataTable> _result = new ArrayList<FavDataTable>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final FavDataTable _item;
            _item = new FavDataTable();
            final int _tmpFavId;
            _tmpFavId = _cursor.getInt(_cursorIndexOfFavId);
            _item.setFavId(_tmpFavId);
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            _item.setProductId(_tmpProductId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            _item.setUserId(_tmpUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public FavDataTable getFavourite(final int productId, final int userId) {
    final String _sql = "Select * from fav_table where product_id=? and user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfFavId = CursorUtil.getColumnIndexOrThrow(_cursor, "favId");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
      final FavDataTable _result;
      if(_cursor.moveToFirst()) {
        _result = new FavDataTable();
        final int _tmpFavId;
        _tmpFavId = _cursor.getInt(_cursorIndexOfFavId);
        _result.setFavId(_tmpFavId);
        final int _tmpProductId;
        _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
        _result.setProductId(_tmpProductId);
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        _result.setUserId(_tmpUserId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
