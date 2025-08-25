package com.appvalence.hayatkurtar.data.mesh.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao;
import com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MeshDatabase_Impl extends MeshDatabase {
  private volatile MessageStoreDao _messageStoreDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `seen_messages` (`messageId` TEXT NOT NULL, `firstSeenAt` INTEGER NOT NULL, `lastSeenAt` INTEGER NOT NULL, PRIMARY KEY(`messageId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `pending_messages` (`messageId` TEXT NOT NULL, `originId` INTEGER NOT NULL, `content` BLOB NOT NULL, `priority` INTEGER NOT NULL, `ttl` INTEGER NOT NULL, `hopCount` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `expiresAt` INTEGER NOT NULL, `targetPeerId` TEXT, `retryCount` INTEGER NOT NULL, `lastRetryAt` INTEGER NOT NULL, `acknowledged` INTEGER NOT NULL, PRIMARY KEY(`messageId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '56977fea4d5151f398abdaf0bd5352c1')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `seen_messages`");
        db.execSQL("DROP TABLE IF EXISTS `pending_messages`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSeenMessages = new HashMap<String, TableInfo.Column>(3);
        _columnsSeenMessages.put("messageId", new TableInfo.Column("messageId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeenMessages.put("firstSeenAt", new TableInfo.Column("firstSeenAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSeenMessages.put("lastSeenAt", new TableInfo.Column("lastSeenAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSeenMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSeenMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSeenMessages = new TableInfo("seen_messages", _columnsSeenMessages, _foreignKeysSeenMessages, _indicesSeenMessages);
        final TableInfo _existingSeenMessages = TableInfo.read(db, "seen_messages");
        if (!_infoSeenMessages.equals(_existingSeenMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "seen_messages(com.appvalence.hayatkurtar.data.mesh.store.SeenMessageEntity).\n"
                  + " Expected:\n" + _infoSeenMessages + "\n"
                  + " Found:\n" + _existingSeenMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsPendingMessages = new HashMap<String, TableInfo.Column>(12);
        _columnsPendingMessages.put("messageId", new TableInfo.Column("messageId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("originId", new TableInfo.Column("originId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("content", new TableInfo.Column("content", "BLOB", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("ttl", new TableInfo.Column("ttl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("hopCount", new TableInfo.Column("hopCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("expiresAt", new TableInfo.Column("expiresAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("targetPeerId", new TableInfo.Column("targetPeerId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("retryCount", new TableInfo.Column("retryCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("lastRetryAt", new TableInfo.Column("lastRetryAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("acknowledged", new TableInfo.Column("acknowledged", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPendingMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPendingMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPendingMessages = new TableInfo("pending_messages", _columnsPendingMessages, _foreignKeysPendingMessages, _indicesPendingMessages);
        final TableInfo _existingPendingMessages = TableInfo.read(db, "pending_messages");
        if (!_infoPendingMessages.equals(_existingPendingMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "pending_messages(com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity).\n"
                  + " Expected:\n" + _infoPendingMessages + "\n"
                  + " Found:\n" + _existingPendingMessages);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "56977fea4d5151f398abdaf0bd5352c1", "77841af70b2a2dbc39bbb25efe179d24");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "seen_messages","pending_messages");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `seen_messages`");
      _db.execSQL("DELETE FROM `pending_messages`");
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
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MessageStoreDao.class, MessageStoreDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MessageStoreDao messageStoreDao() {
    if (_messageStoreDao != null) {
      return _messageStoreDao;
    } else {
      synchronized(this) {
        if(_messageStoreDao == null) {
          _messageStoreDao = new MessageStoreDao_Impl(this);
        }
        return _messageStoreDao;
      }
    }
  }
}
