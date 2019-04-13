package maxter.simrec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    //database: saved_recordings.db
    //table: saved_recording_tb
    //column: _id, name, path, length, created_time

    Context mContext;

    public DBHelper(Context context) {
        super(context, "saved_recordings.db", null, 1);
        Log.i("DBHelper", "ctor");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBHelper", "onCreate");
        String command = "create table saved_recording_tb(_id integer primary key, name text, path text, length integer, created_time integer)";
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBHelper", "onUpgrade");
    }

    public int getRecordCount() {
        SQLiteDatabase db = getReadableDatabase();
        String projection[] = {"_id"};
        Cursor cursor = db.query("saved_recording_tb", projection, null, null, null, null, null);
        int count = cursor.getCount();
        Log.i("DBHelper", "getRecordCount " + count);

        return count;
    }

    public long addRecording(String name, String path, long length) {
        Log.i("DBHelper", "addRecording " + name + " ," + path + " ," + length);

        SQLiteDatabase db = getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("name", name);
        value.put("path", path);
        value.put("length", length);
        value.put("created_time", System.currentTimeMillis());

        long rowId = db.insert("saved_recording_tb", null, value);

        return rowId;
    }

    public RecordInfo getRecordInfoAt(int index) {
        SQLiteDatabase db = getReadableDatabase();
        String projection[] = {"_id", "name", "path", "length", "created_time"};
        Cursor cursor = db.query("saved_recording_tb", projection, null, null, null, null, null);

        if (cursor.moveToPosition(index)) {
            RecordInfo record = new RecordInfo();
            record.mId = cursor.getInt(cursor.getColumnIndex("_id"));
            record.mName = cursor.getString(cursor.getColumnIndex("name"));
            record.mPath = cursor.getString(cursor.getColumnIndex("path"));
            record.mLength = cursor.getInt(cursor.getColumnIndex("length"));
            record.mCreatedTime = cursor.getLong(cursor.getColumnIndex("created_time"));

            cursor.close();
            return record;
        }

        return null;
    }
}
