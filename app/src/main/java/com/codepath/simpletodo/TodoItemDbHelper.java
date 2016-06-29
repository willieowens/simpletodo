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
public class TodoItemDbHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "todo";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TBL_ITEM = "item";

    // Item Table Columns
    private static final String COL_ITEM_ID = "id";
    private static final String COL_ITEM_TEXT = "text";
    private static final String COL_ITEM_DUE = "dueDate";
    private static final String COL_ITEM_PRIORITY = "priority";


    private static TodoItemDbHelper onlyOne;


    public static synchronized TodoItemDbHelper getInstance(Context context) {
        if (onlyOne == null)
            onlyOne = new TodoItemDbHelper(context);
        return onlyOne;
    }

    private TodoItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE =
                "CREATE TABLE " + TBL_ITEM +
                "(" +
                    COL_ITEM_ID + " INTEGER PRIMARY KEY," +
                    COL_ITEM_TEXT + " TEXT," +
                    COL_ITEM_DUE + " TEXT DEFAULT NULL," +   // TODO: date type
                    COL_ITEM_PRIORITY + " TEXT DEFAULT " + TodoItem.Priority.MED.name() +
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
                            cursor.getString(cursor.getColumnIndex(COL_ITEM_DUE)),
                            TodoItem.Priority.valueOf(cursor.getString(cursor.getColumnIndex(COL_ITEM_PRIORITY)))
                    );
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error loading items from db. " + e.getStackTrace());
        }

        return items;
    }

    public void saveItem(TodoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ITEM_TEXT, item.getText());
            values.put(COL_ITEM_DUE, item.getDueDate());
            values.put(COL_ITEM_PRIORITY, item.getPriority().name());

            long id = db.insert(TBL_ITEM, null, values);
            item.setId(((Long)id).intValue());

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error while saving list item: " + e.getStackTrace());
        } finally {
            db.endTransaction();
        }
    }

    public void updateItem(TodoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ITEM_TEXT, item.getText());
            values.put(COL_ITEM_DUE, item.getDueDate());
            values.put(COL_ITEM_PRIORITY, item.getPriority().name());

            db.update(TBL_ITEM, values, "id=?", new String[] { item.getId()+"" });

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error while saving list item: " + e.getStackTrace());
        } finally {
            db.endTransaction();
        }
    }

    public void deleteItem(TodoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TBL_ITEM, "id=?", new String[] { item.getId()+"" });
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.w(getClass().getSimpleName(), "Error deleting item " + item.getId() + " from db");
        } finally {
            db.endTransaction();
        }
    }

    public void writeItems(List<TodoItem> items) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            for (TodoItem item : items) {
                ContentValues values = new ContentValues();
                values.put(COL_ITEM_TEXT, item.getText());
                values.put(COL_ITEM_DUE, item.getDueDate());
                values.put(COL_ITEM_PRIORITY, item.getPriority().name());
                if (item.getId() != null) {
                    values.put(COL_ITEM_ID, item.getId());
                    Log.d(getClass().getSimpleName(), "Updating item " + COL_ITEM_ID + " in db: " + item.toString());
                    db.update(TBL_ITEM, values, COL_ITEM_ID + "=?", new String[] {COL_ITEM_ID});
                } else {
                    long id = db.insert(TBL_ITEM, null, values);
                    Log.d(getClass().getSimpleName(), "Inserted new todo item into db: " + item.toString() + ", with id: " + id);
                    item.setId(((Long)id).intValue());
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Error while storing list items: " + e.getStackTrace());
        } finally {
            db.endTransaction();
        }
    }
}
