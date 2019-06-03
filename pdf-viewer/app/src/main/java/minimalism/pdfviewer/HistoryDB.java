package minimalism.pdfviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HistoryDB extends SQLiteOpenHelper {

    static final String DB_NAME = "history.db";
    static final String TABLE_NAME = "history_tb";
    static final String COLUMN_NAME_URI = "uri";
    static final String COLUMN_NAME_LAST_OPEN_TIME = "last_open_time";

    Context mContext;

    public HistoryDB(Context context) {
        super(context, DB_NAME, null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command = String.format("create table %s(_id integer primary key, %s text, %s integer)", TABLE_NAME, COLUMN_NAME_URI, COLUMN_NAME_LAST_OPEN_TIME);
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String command = String.format("drop table if exists %s", TABLE_NAME);
        db.execSQL(command);

        onCreate(db);
    }

    public void addItem(HistoryItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_URI, item.getUriStr());
        values.put(COLUMN_NAME_LAST_OPEN_TIME, item.getLastOpenTime());

        db.insert(TABLE_NAME, null, values);
    }

    public List<HistoryItem> getAllItem() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { COLUMN_NAME_URI, COLUMN_NAME_LAST_OPEN_TIME };
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);

        List<HistoryItem> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int uriColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME_URI);
            String uriStr = cursor.getString(uriColumnIndex);

            int lastOpenTimeColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME_LAST_OPEN_TIME);
            long lastOpenTime = cursor.getLong(lastOpenTimeColumnIndex);

            HistoryItem item = new HistoryItem(uriStr, lastOpenTime);
            items.add(item);
        }

        cursor.close();

        return items;
    }

    public void removeItem(HistoryItem item) {
        SQLiteDatabase db = getWritableDatabase();

        String uriStr = item.getUriStr();

        String selection = COLUMN_NAME_URI + " LIKE ?";
        String[] slectionArgs = {uriStr};

        db.delete(TABLE_NAME, selection, slectionArgs);
    }
}
