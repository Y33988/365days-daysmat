package com.remcalendar.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.remcalendar.data.database.Converters;
import com.remcalendar.data.model.Anniversary;
import com.remcalendar.data.model.RepeatInterval;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AnniversaryDao_Impl implements AnniversaryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Anniversary> __insertionAdapterOfAnniversary;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Anniversary> __deletionAdapterOfAnniversary;

  private final EntityDeletionOrUpdateAdapter<Anniversary> __updateAdapterOfAnniversary;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAnniversaryById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTopStatus;

  public AnniversaryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAnniversary = new EntityInsertionAdapter<Anniversary>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `anniversaries` (`id`,`title`,`date`,`category`,`icon`,`color`,`isTop`,`repeatInterval`,`isLunar`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Anniversary entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getDate());
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getIcon());
        statement.bindLong(6, entity.getColor());
        final int _tmp = entity.isTop() ? 1 : 0;
        statement.bindLong(7, _tmp);
        final String _tmp_1 = __converters.fromRepeatInterval(entity.getRepeatInterval());
        statement.bindString(8, _tmp_1);
        final int _tmp_2 = entity.isLunar() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfAnniversary = new EntityDeletionOrUpdateAdapter<Anniversary>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `anniversaries` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Anniversary entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAnniversary = new EntityDeletionOrUpdateAdapter<Anniversary>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `anniversaries` SET `id` = ?,`title` = ?,`date` = ?,`category` = ?,`icon` = ?,`color` = ?,`isTop` = ?,`repeatInterval` = ?,`isLunar` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Anniversary entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getDate());
        statement.bindString(4, entity.getCategory());
        statement.bindString(5, entity.getIcon());
        statement.bindLong(6, entity.getColor());
        final int _tmp = entity.isTop() ? 1 : 0;
        statement.bindLong(7, _tmp);
        final String _tmp_1 = __converters.fromRepeatInterval(entity.getRepeatInterval());
        statement.bindString(8, _tmp_1);
        final int _tmp_2 = entity.isLunar() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAnniversaryById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM anniversaries WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTopStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE anniversaries SET isTop = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAnniversary(final Anniversary anniversary,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAnniversary.insertAndReturnId(anniversary);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAnniversary(final Anniversary anniversary,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAnniversary.handle(anniversary);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAnniversary(final Anniversary anniversary,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAnniversary.handle(anniversary);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAnniversaryById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAnniversaryById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteAnniversaryById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTopStatus(final long id, final boolean isTop,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTopStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isTop ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfUpdateTopStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Anniversary>> getAllAnniversaries() {
    final String _sql = "SELECT * FROM anniversaries ORDER BY isTop DESC, date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"anniversaries"}, new Callable<List<Anniversary>>() {
      @Override
      @NonNull
      public List<Anniversary> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsTop = CursorUtil.getColumnIndexOrThrow(_cursor, "isTop");
          final int _cursorIndexOfRepeatInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatInterval");
          final int _cursorIndexOfIsLunar = CursorUtil.getColumnIndexOrThrow(_cursor, "isLunar");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Anniversary> _result = new ArrayList<Anniversary>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Anniversary _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsTop;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsTop);
            _tmpIsTop = _tmp != 0;
            final RepeatInterval _tmpRepeatInterval;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRepeatInterval);
            _tmpRepeatInterval = __converters.toRepeatInterval(_tmp_1);
            final boolean _tmpIsLunar;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsLunar);
            _tmpIsLunar = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Anniversary(_tmpId,_tmpTitle,_tmpDate,_tmpCategory,_tmpIcon,_tmpColor,_tmpIsTop,_tmpRepeatInterval,_tmpIsLunar,_tmpCreatedAt);
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
  public Object getAnniversaryById(final long id,
      final Continuation<? super Anniversary> $completion) {
    final String _sql = "SELECT * FROM anniversaries WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Anniversary>() {
      @Override
      @Nullable
      public Anniversary call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsTop = CursorUtil.getColumnIndexOrThrow(_cursor, "isTop");
          final int _cursorIndexOfRepeatInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatInterval");
          final int _cursorIndexOfIsLunar = CursorUtil.getColumnIndexOrThrow(_cursor, "isLunar");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Anniversary _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsTop;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsTop);
            _tmpIsTop = _tmp != 0;
            final RepeatInterval _tmpRepeatInterval;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRepeatInterval);
            _tmpRepeatInterval = __converters.toRepeatInterval(_tmp_1);
            final boolean _tmpIsLunar;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsLunar);
            _tmpIsLunar = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Anniversary(_tmpId,_tmpTitle,_tmpDate,_tmpCategory,_tmpIcon,_tmpColor,_tmpIsTop,_tmpRepeatInterval,_tmpIsLunar,_tmpCreatedAt);
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
  public Flow<List<Anniversary>> searchAnniversaries(final String query) {
    final String _sql = "SELECT * FROM anniversaries WHERE title LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"anniversaries"}, new Callable<List<Anniversary>>() {
      @Override
      @NonNull
      public List<Anniversary> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsTop = CursorUtil.getColumnIndexOrThrow(_cursor, "isTop");
          final int _cursorIndexOfRepeatInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatInterval");
          final int _cursorIndexOfIsLunar = CursorUtil.getColumnIndexOrThrow(_cursor, "isLunar");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Anniversary> _result = new ArrayList<Anniversary>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Anniversary _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsTop;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsTop);
            _tmpIsTop = _tmp != 0;
            final RepeatInterval _tmpRepeatInterval;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRepeatInterval);
            _tmpRepeatInterval = __converters.toRepeatInterval(_tmp_1);
            final boolean _tmpIsLunar;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsLunar);
            _tmpIsLunar = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Anniversary(_tmpId,_tmpTitle,_tmpDate,_tmpCategory,_tmpIcon,_tmpColor,_tmpIsTop,_tmpRepeatInterval,_tmpIsLunar,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
