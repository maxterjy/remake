package maxter.simrec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    //database: saved_recordings.db
    //table: saved_recording_tb
    //column: _id, name, path, length, created_time

    //make DBHelper singleton
    static DBHelper mInstance = null;

    Context mContext;
    OnDatabaseChangedListener mOnDatabaseListener = null;

    //cache records info
    ArrayList<RecordInfo> mRecords = null;
    final int NOT_INIT_RECORD_COUNT = -1;
    int mRecordCount = NOT_INIT_RECORD_COUNT;

    private DBHelper(Context context) {
        super(context, "saved_recordings.db", null, 1);
        mContext = context;

        mRecords = new ArrayList<RecordInfo>();
    }

    public static void initInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
    }

    public static DBHelper getInstance() {
        return mInstance;
    }
    //make DBHelper singleton end

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = "create table saved_recording_tb(_id integer primary key, name text, path text, length integer, created_time integer)";
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public int getRecordCount() {
        if (mRecordCount == NOT_INIT_RECORD_COUNT) {
            SQLiteDatabase db = getReadableDatabase();
            String projection[] = {"_id"};
            Cursor cursor = db.query("saved_recording_tb", projection, null, null, null, null, null);
            mRecordCount = cursor.getCount();
        }

        return mRecordCount;
    }

    public long addRecording(String name, String path, long length) {
        //Add to database
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("name", name);
        value.put("path", path);
        value.put("length", length);
        value.put("created_time", System.currentTimeMillis());
        long rowId = db.insert("saved_recording_tb", null, value);

        //Add to cache
        mRecords.add(0, new RecordInfo(name, path, length, System.currentTimeMillis()));
        mRecordCount++;

        if (mOnDatabaseListener != null)
            mOnDatabaseListener.onNewEntryAdded();

        return rowId;
    }

    public RecordInfo getRecordInfoAt(int index) {
        try {
            //find record from cache first
            RecordInfo record = mRecords.get(index);
            return record;

        } catch (Exception ex) {
            //if not found in cache, find in database
            SQLiteDatabase db = getReadableDatabase();
            String projection[] = {"_id", "name", "path", "length", "created_time"};
            Cursor cursor = db.query("saved_recording_tb", projection, null, null, null, null, "_id desc");

            if (cursor.moveToPosition(index)) {
                RecordInfo record = new RecordInfo();
                record.mId = cursor.getInt(cursor.getColumnIndex("_id"));
                record.mName = cursor.getString(cursor.getColumnIndex("name"));
                record.mPath = cursor.getString(cursor.getColumnIndex("path"));
                record.mLength = cursor.getInt(cursor.getColumnIndex("length"));
                record.mCreatedTime = cursor.getLong(cursor.getColumnIndex("created_time"));

                cursor.close();

                //add record to cache
                mRecords.add(0, record);

                return record;
           }
        }

        return null;
    }

    public interface OnDatabaseChangedListener {
        void onNewEntryAdded();
    }

    public void setOnDatabaseChangedListener(OnDatabaseChangedListener listener) {
        mOnDatabaseListener = listener;
    }
}
