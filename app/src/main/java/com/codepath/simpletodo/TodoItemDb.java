package com.codepath.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wowens on 6/20/16.
 */
public class TodoItemDb extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "todo";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TBL_ITEM = "item";

    // Item Table Columns
    private static final String COL_ITEM_ID = "id";
    private static final String COL_ITEM_TEXT = "text";
    private static final String COL_ITEM_DUE = "dueDate";


    private static TodoItemDb onlyOne;


    public static synchronized TodoItemDb getInstance(Context context) {
        if (onlyOne == null)
            onlyOne = new TodoItemDb(context);
        return onlyOne;
    }

    private TodoItemDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE =
                "CREATE TABLE " + TBL_ITEM +
                "(" +
                    COL_ITEM_ID + " INTEGER PRIMARY KEY," +
                    COL_ITEM_TEXT + " TEXT," +
                    COL_ITEM_DUE + " TEXT DEFAULT NULL" +   // TODO: date type
                ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // DAO Methods
    public List<TodoItem> getItems() {
        List<TodoItem> items = new ArrayList<TodoItem>();

        String SQL_GET_ITEM =
                "SELECT * FROM " + TBL_ITEM + ";";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_GET_ITEM, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TodoItem item = new TodoItem(
                            cursor.getInt(cursor.getColumnIndex(COL_ITEM_ID)),
                            cursor.getString(cursor.getColumnIndex(COL_ITEM_TEXT)),
                            cursor.getString(cursor.getColumnIndex(COL_ITEM_DUE))
                    );
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error loading items from db. " + e.getStackTrace());
        }

        return items;
    }

    public void writeItems(List<TodoItem> items) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            // For now, delete and resave everything
            db.delete(TBL_ITEM, null, null);
            for (TodoItem item : items) {
                ContentValues values = new ContentValues();
                if (item.getId() != null)
                    values.put(COL_ITEM_ID, item.getId());
                values.put(COL_ITEM_TEXT, item.getText());
                values.put(COL_ITEM_DUE, item.getDueDate());
                db.insert(TBL_ITEM, null, values);
            }
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error while storing list items: " + e.getStackTrace());
        } finally {
            db.endTransaction();
        }
    }
}
