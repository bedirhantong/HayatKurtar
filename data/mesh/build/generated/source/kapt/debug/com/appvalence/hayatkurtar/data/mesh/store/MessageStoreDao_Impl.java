package com.appvalence.hayatkurtar.data.mesh.store;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MessageStoreDao_Impl implements MessageStoreDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SeenMessageEntity> __insertionAdapterOfSeenMessageEntity;

  private final EntityInsertionAdapter<PendingMessageEntity> __insertionAdapterOfPendingMessageEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldSeenMessages;

  private final SharedSQLiteStatement __preparedStmtOfMarkMessageAcknowledged;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRetryInfo;

  private final SharedSQLiteStatement __preparedStmtOfDeletePendingMessage;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExpiredMessages;

  public MessageStoreDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSeenMessageEntity = new EntityInsertionAdapter<SeenMessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `seen_messages` (`messageId`,`firstSeenAt`,`lastSeenAt`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SeenMessageEntity entity) {
        if (entity.getMessageId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMessageId());
        }
        statement.bindLong(2, entity.getFirstSeenAt());
        statement.bindLong(3, entity.getLastSeenAt());
      }
    };
    this.__insertionAdapterOfPendingMessageEntity = new EntityInsertionAdapter<PendingMessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pending_messages` (`messageId`,`originId`,`content`,`priority`,`ttl`,`hopCount`,`timestamp`,`expiresAt`,`targetPeerId`,`retryCount`,`lastRetryAt`,`acknowledged`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PendingMessageEntity entity) {
        if (entity.getMessageId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMessageId());
        }
        statement.bindLong(2, entity.getOriginId());
        if (entity.getContent() == null) {
          statement.bindNull(3);
        } else {
          statement.bindBlob(3, entity.getContent());
        }
        statement.bindLong(4, entity.getPriority());
        statement.bindLong(5, entity.getTtl());
        statement.bindLong(6, entity.getHopCount());
        statement.bindLong(7, entity.getTimestamp());
        statement.bindLong(8, entity.getExpiresAt());
        if (entity.getTargetPeerId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTargetPeerId());
        }
        statement.bindLong(10, entity.getRetryCount());
        statement.bindLong(11, entity.getLastRetryAt());
        final int _tmp = entity.getAcknowledged() ? 1 : 0;
        statement.bindLong(12, _tmp);
      }
    };
    this.__preparedStmtOfDeleteOldSeenMessages = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM seen_messages WHERE firstSeenAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkMessageAcknowledged = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pending_messages SET acknowledged = 1 WHERE messageId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateRetryInfo = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pending_messages SET retryCount = ?, lastRetryAt = ? WHERE messageId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePendingMessage = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pending_messages WHERE messageId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteExpiredMessages = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pending_messages WHERE expiresAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSeenMessage(final SeenMessageEntity message,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSeenMessageEntity.insert(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPendingMessage(final PendingMessageEntity message,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPendingMessageEntity.insert(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldSeenMessages(final long cutoffTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldSeenMessages.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cutoffTime);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldSeenMessages.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markMessageAcknowledged(final String messageId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkMessageAcknowledged.acquire();
        int _argIndex = 1;
        if (messageId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, messageId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkMessageAcknowledged.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRetryInfo(final String messageId, final int retryCount, final long retryTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRetryInfo.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, retryCount);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, retryTime);
        _argIndex = 3;
        if (messageId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, messageId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateRetryInfo.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePendingMessage(final String messageId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePendingMessage.acquire();
        int _argIndex = 1;
        if (messageId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, messageId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePendingMessage.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExpiredMessages(final long currentTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExpiredMessages.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, currentTime);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteExpiredMessages.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getSeenMessageCount(final String messageId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM seen_messages WHERE messageId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (messageId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, messageId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSeenMessagesCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM seen_messages";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPendingMessagesForPeer(final String peerId,
      final Continuation<? super List<PendingMessageEntity>> $completion) {
    final String _sql = "SELECT * FROM pending_messages WHERE targetPeerId = ? AND acknowledged = 0 ORDER BY priority DESC, timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (peerId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, peerId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PendingMessageEntity>>() {
      @Override
      @NonNull
      public List<PendingMessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfOriginId = CursorUtil.getColumnIndexOrThrow(_cursor, "originId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final int _cursorIndexOfTargetPeerId = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPeerId");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfLastRetryAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastRetryAt");
          final int _cursorIndexOfAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "acknowledged");
          final List<PendingMessageEntity> _result = new ArrayList<PendingMessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PendingMessageEntity _item;
            final String _tmpMessageId;
            if (_cursor.isNull(_cursorIndexOfMessageId)) {
              _tmpMessageId = null;
            } else {
              _tmpMessageId = _cursor.getString(_cursorIndexOfMessageId);
            }
            final long _tmpOriginId;
            _tmpOriginId = _cursor.getLong(_cursorIndexOfOriginId);
            final byte[] _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getBlob(_cursorIndexOfContent);
            }
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final int _tmpTtl;
            _tmpTtl = _cursor.getInt(_cursorIndexOfTtl);
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpExpiresAt;
            _tmpExpiresAt = _cursor.getLong(_cursorIndexOfExpiresAt);
            final String _tmpTargetPeerId;
            if (_cursor.isNull(_cursorIndexOfTargetPeerId)) {
              _tmpTargetPeerId = null;
            } else {
              _tmpTargetPeerId = _cursor.getString(_cursorIndexOfTargetPeerId);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final long _tmpLastRetryAt;
            _tmpLastRetryAt = _cursor.getLong(_cursorIndexOfLastRetryAt);
            final boolean _tmpAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAcknowledged);
            _tmpAcknowledged = _tmp != 0;
            _item = new PendingMessageEntity(_tmpMessageId,_tmpOriginId,_tmpContent,_tmpPriority,_tmpTtl,_tmpHopCount,_tmpTimestamp,_tmpExpiresAt,_tmpTargetPeerId,_tmpRetryCount,_tmpLastRetryAt,_tmpAcknowledged);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllPendingMessages(
      final Continuation<? super List<PendingMessageEntity>> $completion) {
    final String _sql = "SELECT * FROM pending_messages WHERE acknowledged = 0 ORDER BY priority DESC, timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PendingMessageEntity>>() {
      @Override
      @NonNull
      public List<PendingMessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfOriginId = CursorUtil.getColumnIndexOrThrow(_cursor, "originId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final int _cursorIndexOfTargetPeerId = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPeerId");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfLastRetryAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastRetryAt");
          final int _cursorIndexOfAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "acknowledged");
          final List<PendingMessageEntity> _result = new ArrayList<PendingMessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PendingMessageEntity _item;
            final String _tmpMessageId;
            if (_cursor.isNull(_cursorIndexOfMessageId)) {
              _tmpMessageId = null;
            } else {
              _tmpMessageId = _cursor.getString(_cursorIndexOfMessageId);
            }
            final long _tmpOriginId;
            _tmpOriginId = _cursor.getLong(_cursorIndexOfOriginId);
            final byte[] _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getBlob(_cursorIndexOfContent);
            }
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final int _tmpTtl;
            _tmpTtl = _cursor.getInt(_cursorIndexOfTtl);
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpExpiresAt;
            _tmpExpiresAt = _cursor.getLong(_cursorIndexOfExpiresAt);
            final String _tmpTargetPeerId;
            if (_cursor.isNull(_cursorIndexOfTargetPeerId)) {
              _tmpTargetPeerId = null;
            } else {
              _tmpTargetPeerId = _cursor.getString(_cursorIndexOfTargetPeerId);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final long _tmpLastRetryAt;
            _tmpLastRetryAt = _cursor.getLong(_cursorIndexOfLastRetryAt);
            final boolean _tmpAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAcknowledged);
            _tmpAcknowledged = _tmp != 0;
            _item = new PendingMessageEntity(_tmpMessageId,_tmpOriginId,_tmpContent,_tmpPriority,_tmpTtl,_tmpHopCount,_tmpTimestamp,_tmpExpiresAt,_tmpTargetPeerId,_tmpRetryCount,_tmpLastRetryAt,_tmpAcknowledged);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPendingAcknowledgments(final long cutoffTime,
      final Continuation<? super List<PendingMessageEntity>> $completion) {
    final String _sql = "SELECT * FROM pending_messages WHERE acknowledged = 0 AND lastRetryAt < ? ORDER BY priority DESC, timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cutoffTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PendingMessageEntity>>() {
      @Override
      @NonNull
      public List<PendingMessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfOriginId = CursorUtil.getColumnIndexOrThrow(_cursor, "originId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfTtl = CursorUtil.getColumnIndexOrThrow(_cursor, "ttl");
          final int _cursorIndexOfHopCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hopCount");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final int _cursorIndexOfTargetPeerId = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPeerId");
          final int _cursorIndexOfRetryCount = CursorUtil.getColumnIndexOrThrow(_cursor, "retryCount");
          final int _cursorIndexOfLastRetryAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastRetryAt");
          final int _cursorIndexOfAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "acknowledged");
          final List<PendingMessageEntity> _result = new ArrayList<PendingMessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PendingMessageEntity _item;
            final String _tmpMessageId;
            if (_cursor.isNull(_cursorIndexOfMessageId)) {
              _tmpMessageId = null;
            } else {
              _tmpMessageId = _cursor.getString(_cursorIndexOfMessageId);
            }
            final long _tmpOriginId;
            _tmpOriginId = _cursor.getLong(_cursorIndexOfOriginId);
            final byte[] _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getBlob(_cursorIndexOfContent);
            }
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final int _tmpTtl;
            _tmpTtl = _cursor.getInt(_cursorIndexOfTtl);
            final int _tmpHopCount;
            _tmpHopCount = _cursor.getInt(_cursorIndexOfHopCount);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpExpiresAt;
            _tmpExpiresAt = _cursor.getLong(_cursorIndexOfExpiresAt);
            final String _tmpTargetPeerId;
            if (_cursor.isNull(_cursorIndexOfTargetPeerId)) {
              _tmpTargetPeerId = null;
            } else {
              _tmpTargetPeerId = _cursor.getString(_cursorIndexOfTargetPeerId);
            }
            final int _tmpRetryCount;
            _tmpRetryCount = _cursor.getInt(_cursorIndexOfRetryCount);
            final long _tmpLastRetryAt;
            _tmpLastRetryAt = _cursor.getLong(_cursorIndexOfLastRetryAt);
            final boolean _tmpAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAcknowledged);
            _tmpAcknowledged = _tmp != 0;
            _item = new PendingMessageEntity(_tmpMessageId,_tmpOriginId,_tmpContent,_tmpPriority,_tmpTtl,_tmpHopCount,_tmpTimestamp,_tmpExpiresAt,_tmpTargetPeerId,_tmpRetryCount,_tmpLastRetryAt,_tmpAcknowledged);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPendingMessagesCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM pending_messages WHERE acknowledged = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPendingAcksCount(final long cutoffTime,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM pending_messages WHERE acknowledged = 0 AND lastRetryAt < ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cutoffTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
