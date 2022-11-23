package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseHelper extends SQLiteOpenHelper {

    private  static final String DATABASE_NAME = "noteManager";
    private  static final int DATABASE_VERSION = 1;

    //chứa tên các cột
    private static final String TABLE_NAME = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_CONTENT = "Content";
    private static final String NEW_KEY = "key";

    public NoteDatabaseHelper (Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        String ALTER_TABLE = "ALTER TABLE "+TABLE_NAME+" ADD COLUMN " +NEW_KEY+"TEXT";
        db.execSQL(ALTER_TABLE);
    }

    public void addNotes(Note notes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,notes.getTitle());
        values.put(KEY_CONTENT,notes.getContent());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public List<Note> getAllNotes(){
        List<Note> noteList = new ArrayList<>();

        String selectQuery= "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                Note note = new Note(id, title, content);

                noteList.add(note);
            }
            while (cursor.moveToNext());
        }
        return noteList;
    }

    public int update (Note notes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,notes.getTitle());
        values.put(KEY_CONTENT,notes.getContent());

        return db.update(TABLE_NAME,values,KEY_ID+ " =? ", new String[]{String.valueOf(notes.getTitle())});
    }

    public void deleteNote(Note notes){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,KEY_ID+ " =? ", new String[]{String.valueOf(notes.getTitle())});
        db.close();
    }
}
