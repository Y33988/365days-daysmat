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
import com.remcalendar.data.model.Event;
import com.remcalendar.data.model.RepeatRule;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class EventDao_Impl implements EventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Event> __insertionAdapterOfEvent;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Event> __deletionAdapterOfEvent;

  private final EntityDeletionOrUpdateAdapter<Event> __updateAdapterOfEvent;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEventById;

  public EventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEvent = new EntityInsertionAdapter<Event>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `events` (`id`,`title`,`date`,`time`,`location`,`description`,`category`,`color`,`isAllDay`,`reminderMinutes`,`repeatRule`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Event entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getDate());
        if (entity.getTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getTime());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocation());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDescription());
        }
        statement.bindString(7, entity.getCategory());
        statement.bindLong(8, entity.getColor());
        final int _tmp = entity.isAllDay() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getReminderMinutes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getReminderMinutes());
        }
        final String _tmp_1 = __converters.fromRepeatRule(entity.getRepeatRule());
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_1);
        }
        statement.bindLong(12, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfEvent = new EntityDeletionOrUpdateAdapter<Event>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `events` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Event entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfEvent = new EntityDeletionOrUpdateAdapter<Event>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `events` SET `id` = ?,`title` = ?,`date` = ?,`time` = ?,`location` = ?,`description` = ?,`category` = ?,`color` = ?,`isAllDay` = ?,`reminderMinutes` = ?,`repeatRule` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Event entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getDate());
        if (entity.getTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getTime());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocation());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDescription());
        }
        statement.bindString(7, entity.getCategory());
        statement.bindLong(8, entity.getColor());
        final int _tmp = entity.isAllDay() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getReminderMinutes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getReminderMinutes());
        }
        final String _tmp_1 = __converters.fromRepeatRule(entity.getRepeatRule());
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_1);
        }
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteEventById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM events WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertEvent(final Event event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEvent.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEvent(final Event event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEvent(final Event event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEventById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEventById.acquire();
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
          __preparedStmtOfDeleteEventById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Event>> getAllEvents() {
    final String _sql = "SELECT * FROM events ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<Event>>() {
      @Override
      @NonNull
      public List<Event> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsAllDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAllDay");
          final int _cursorIndexOfReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutes");
          final int _cursorIndexOfRepeatRule = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatRule");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Event> _result = new ArrayList<Event>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Event _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsAllDay;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAllDay);
            _tmpIsAllDay = _tmp != 0;
            final Integer _tmpReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfReminderMinutes)) {
              _tmpReminderMinutes = null;
            } else {
              _tmpReminderMinutes = _cursor.getInt(_cursorIndexOfReminderMinutes);
            }
            final RepeatRule _tmpRepeatRule;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRepeatRule)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRepeatRule);
            }
            _tmpRepeatRule = __converters.toRepeatRule(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Event(_tmpId,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpDescription,_tmpCategory,_tmpColor,_tmpIsAllDay,_tmpReminderMinutes,_tmpRepeatRule,_tmpCreatedAt);
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
  public Object getEventById(final long id, final Continuation<? super Event> $completion) {
    final String _sql = "SELECT * FROM events WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Event>() {
      @Override
      @Nullable
      public Event call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsAllDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAllDay");
          final int _cursorIndexOfReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutes");
          final int _cursorIndexOfRepeatRule = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatRule");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Event _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsAllDay;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAllDay);
            _tmpIsAllDay = _tmp != 0;
            final Integer _tmpReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfReminderMinutes)) {
              _tmpReminderMinutes = null;
            } else {
              _tmpReminderMinutes = _cursor.getInt(_cursorIndexOfReminderMinutes);
            }
            final RepeatRule _tmpRepeatRule;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRepeatRule)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRepeatRule);
            }
            _tmpRepeatRule = __converters.toRepeatRule(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Event(_tmpId,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpDescription,_tmpCategory,_tmpColor,_tmpIsAllDay,_tmpReminderMinutes,_tmpRepeatRule,_tmpCreatedAt);
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
  public Flow<List<Event>> getEventsByDateRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM events WHERE date BETWEEN ? AND ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<Event>>() {
      @Override
      @NonNull
      public List<Event> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsAllDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAllDay");
          final int _cursorIndexOfReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutes");
          final int _cursorIndexOfRepeatRule = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatRule");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Event> _result = new ArrayList<Event>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Event _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsAllDay;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAllDay);
            _tmpIsAllDay = _tmp != 0;
            final Integer _tmpReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfReminderMinutes)) {
              _tmpReminderMinutes = null;
            } else {
              _tmpReminderMinutes = _cursor.getInt(_cursorIndexOfReminderMinutes);
            }
            final RepeatRule _tmpRepeatRule;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRepeatRule)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRepeatRule);
            }
            _tmpRepeatRule = __converters.toRepeatRule(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Event(_tmpId,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpDescription,_tmpCategory,_tmpColor,_tmpIsAllDay,_tmpReminderMinutes,_tmpRepeatRule,_tmpCreatedAt);
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
  public Flow<List<Event>> searchEvents(final String query) {
    final String _sql = "SELECT * FROM events WHERE title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<Event>>() {
      @Override
      @NonNull
      public List<Event> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsAllDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isAllDay");
          final int _cursorIndexOfReminderMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutes");
          final int _cursorIndexOfRepeatRule = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatRule");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Event> _result = new ArrayList<Event>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Event _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsAllDay;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAllDay);
            _tmpIsAllDay = _tmp != 0;
            final Integer _tmpReminderMinutes;
            if (_cursor.isNull(_cursorIndexOfReminderMinutes)) {
              _tmpReminderMinutes = null;
            } else {
              _tmpReminderMinutes = _cursor.getInt(_cursorIndexOfReminderMinutes);
            }
            final RepeatRule _tmpRepeatRule;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRepeatRule)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRepeatRule);
            }
            _tmpRepeatRule = __converters.toRepeatRule(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Event(_tmpId,_tmpTitle,_tmpDate,_tmpTime,_tmpLocation,_tmpDescription,_tmpCategory,_tmpColor,_tmpIsAllDay,_tmpReminderMinutes,_tmpRepeatRule,_tmpCreatedAt);
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
