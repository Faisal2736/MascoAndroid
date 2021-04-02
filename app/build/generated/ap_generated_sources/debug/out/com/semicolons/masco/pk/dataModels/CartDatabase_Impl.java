package com.semicolons.masco.pk.dataModels;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CartDatabase_Impl extends CartDatabase {
  private volatile CartDao _cartDao;

  private volatile FavDao _favDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cart_table` (`cartId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `product_id` INTEGER NOT NULL, `product_quantity` INTEGER NOT NULL, `user_id` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `fav_table` (`favId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `product_id` INTEGER NOT NULL, `user_id` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '944e1ee729689c0afd75b1b98385ad48')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `cart_table`");
        _db.execSQL("DROP TABLE IF EXISTS `fav_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCartTable = new HashMap<String, TableInfo.Column>(4);
        _columnsCartTable.put("cartId", new TableInfo.Column("cartId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartTable.put("product_id", new TableInfo.Column("product_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartTable.put("product_quantity", new TableInfo.Column("product_quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartTable.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCartTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCartTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCartTable = new TableInfo("cart_table", _columnsCartTable, _foreignKeysCartTable, _indicesCartTable);
        final TableInfo _existingCartTable = TableInfo.read(_db, "cart_table");
        if (! _infoCartTable.equals(_existingCartTable)) {
          return new RoomOpenHelper.ValidationResult(false, "cart_table(com.semicolons.masco.pk.dataModels.CartDataTable).\n"
                  + " Expected:\n" + _infoCartTable + "\n"
                  + " Found:\n" + _existingCartTable);
        }
        final HashMap<String, TableInfo.Column> _columnsFavTable = new HashMap<String, TableInfo.Column>(3);
        _columnsFavTable.put("favId", new TableInfo.Column("favId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavTable.put("product_id", new TableInfo.Column("product_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavTable.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavTable = new TableInfo("fav_table", _columnsFavTable, _foreignKeysFavTable, _indicesFavTable);
        final TableInfo _existingFavTable = TableInfo.read(_db, "fav_table");
        if (! _infoFavTable.equals(_existingFavTable)) {
          return new RoomOpenHelper.ValidationResult(false, "fav_table(com.semicolons.masco.pk.dataModels.FavDataTable).\n"
                  + " Expected:\n" + _infoFavTable + "\n"
                  + " Found:\n" + _existingFavTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "944e1ee729689c0afd75b1b98385ad48", "ccf1a2405fb29335458de39c7ba3ce3d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cart_table","fav_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `cart_table`");
      _db.execSQL("DELETE FROM `fav_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public CartDao cartDao() {
    if (_cartDao != null) {
      return _cartDao;
    } else {
      synchronized(this) {
        if(_cartDao == null) {
          _cartDao = new CartDao_Impl(this);
        }
        return _cartDao;
      }
    }
  }

  @Override
  public FavDao favDao() {
    if (_favDao != null) {
      return _favDao;
    } else {
      synchronized(this) {
        if(_favDao == null) {
          _favDao = new FavDao_Impl(this);
        }
        return _favDao;
      }
    }
  }
}
