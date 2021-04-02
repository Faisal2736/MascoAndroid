package com.semicolons.masco.pk.dataModels;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CartDao_Impl implements CartDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CartDataTable> __insertionAdapterOfCartDataTable;

  private final EntityDeletionOrUpdateAdapter<CartDataTable> __deletionAdapterOfCartDataTable;

  private final EntityDeletionOrUpdateAdapter<CartDataTable> __updateAdapterOfCartDataTable;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllCart;

  private final SharedSQLiteStatement __preparedStmtOfCartforUpdate;

  public CartDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCartDataTable = new EntityInsertionAdapter<CartDataTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `cart_table` (`cartId`,`product_id`,`product_quantity`,`user_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CartDataTable value) {
        stmt.bindLong(1, value.getCartId());
        stmt.bindLong(2, value.getProductId());
        stmt.bindLong(3, value.getProductQuantity());
        stmt.bindLong(4, value.getUserId());
      }
    };
    this.__deletionAdapterOfCartDataTable = new EntityDeletionOrUpdateAdapter<CartDataTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `cart_table` WHERE `cartId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CartDataTable value) {
        stmt.bindLong(1, value.getCartId());
      }
    };
    this.__updateAdapterOfCartDataTable = new EntityDeletionOrUpdateAdapter<CartDataTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `cart_table` SET `cartId` = ?,`product_id` = ?,`product_quantity` = ?,`user_id` = ? WHERE `cartId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CartDataTable value) {
        stmt.bindLong(1, value.getCartId());
        stmt.bindLong(2, value.getProductId());
        stmt.bindLong(3, value.getProductQuantity());
        stmt.bindLong(4, value.getUserId());
        stmt.bindLong(5, value.getCartId());
      }
    };
    this.__preparedStmtOfDeleteAllCart = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from cart_table";
        return _query;
      }
    };
    this.__preparedStmtOfCartforUpdate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "update Cart_Table set product_quantity=?  where  user_id=? and product_id=? ";
        return _query;
      }
    };
  }

  @Override
  public void insert(final CartDataTable cartDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCartDataTable.insert(cartDataTable);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final CartDataTable cartDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCartDataTable.handle(cartDataTable);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final CartDataTable cartDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCartDataTable.handle(cartDataTable);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllCart() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllCart.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllCart.release(_stmt);
    }
  }

  @Override
  public void cartforUpdate(final int productQuantity, final int userId, final int productId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfCartforUpdate.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, productQuantity);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, userId);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, productId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfCartforUpdate.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CartDataTable>> getCartsList(final int userId) {
    final String _sql = "select * from cart_table where user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"cart_table"}, false, new Callable<List<CartDataTable>>() {
      @Override
      public List<CartDataTable> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCartId = CursorUtil.getColumnIndexOrThrow(_cursor, "cartId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfProductQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "product_quantity");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final List<CartDataTable> _result = new ArrayList<CartDataTable>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CartDataTable _item;
            final int _tmpCartId;
            _tmpCartId = _cursor.getInt(_cursorIndexOfCartId);
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final int _tmpProductQuantity;
            _tmpProductQuantity = _cursor.getInt(_cursorIndexOfProductQuantity);
            _item = new CartDataTable(_tmpCartId,_tmpProductId,_tmpProductQuantity);
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
  public LiveData<List<Integer>> getProductIds(final int userId) {
    final String _sql = "select product_id from cart_table where user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"cart_table"}, false, new Callable<List<Integer>>() {
      @Override
      public List<Integer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<Integer> _result = new ArrayList<Integer>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Integer _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getInt(0);
            }
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
  public LiveData<Integer> getCartCount(final int userId) {
    final String _sql = "select Count(DISTINCT product_id) from cart_table where user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"cart_table"}, false, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public LiveData<CartDataTable> getCart(final int productId, final int userId) {
    final String _sql = "select * from cart_table where product_id==? and  user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"cart_table"}, false, new Callable<CartDataTable>() {
      @Override
      public CartDataTable call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCartId = CursorUtil.getColumnIndexOrThrow(_cursor, "cartId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfProductQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "product_quantity");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final CartDataTable _result;
          if(_cursor.moveToFirst()) {
            final int _tmpCartId;
            _tmpCartId = _cursor.getInt(_cursorIndexOfCartId);
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final int _tmpProductQuantity;
            _tmpProductQuantity = _cursor.getInt(_cursorIndexOfProductQuantity);
            _result = new CartDataTable(_tmpCartId,_tmpProductId,_tmpProductQuantity);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            _result.setUserId(_tmpUserId);
          } else {
            _result = null;
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
  public CartDataTable getCart2(final int productId, final int userId) {
    final String _sql = "select * from cart_table where product_id==? and user_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCartId = CursorUtil.getColumnIndexOrThrow(_cursor, "cartId");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
      final int _cursorIndexOfProductQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "product_quantity");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
      final CartDataTable _result;
      if(_cursor.moveToFirst()) {
        final int _tmpCartId;
        _tmpCartId = _cursor.getInt(_cursorIndexOfCartId);
        final int _tmpProductId;
        _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
        final int _tmpProductQuantity;
        _tmpProductQuantity = _cursor.getInt(_cursorIndexOfProductQuantity);
        _result = new CartDataTable(_tmpCartId,_tmpProductId,_tmpProductQuantity);
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
