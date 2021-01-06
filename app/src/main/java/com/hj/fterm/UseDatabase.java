package com.hj.fterm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * SQLite 관련 메소드
 * 명령문 실행
 */
public class UseDatabase {

    private static UseDatabase database;
    public static String TABLE_NAME = "diary";
    public static int VERSION = 2;
    String dbName = "diaryDB.db";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    private UseDatabase(Context context) {
        this.context = context;
    }
    public static UseDatabase getInstance(Context context) {
        if(database == null)
            database = new UseDatabase(context);
        return database;
    }

    public boolean open() {
        System.out.println("opening database [" + dbName + "]");
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return true;
    }
    public void close() {
        System.out.println("closing database [" + dbName + "]");
        db.close();
        database = null;
    }

    public Cursor rawQuery(String sql) {
        System.out.println("executeQuery called");
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(sql, null);
            System.out.println("cursor count = " + cursor.getCount());
        }catch (Exception e){
            System.out.println("Exception in executeQuery");
        }
        return cursor;
    }

    public boolean execSQL(String sql) { // 명령 실행
        System.out.println("execute called");
        try {
            System.out.println("SQL : " + sql);
            db.execSQL(sql);
        }catch (Exception e){
            System.out.println("Exception in executeQuery");
            return false;
        }
        System.out.println("execute success");
        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, dbName, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            System.out.println("creating database" + dbName + "]");
            System.out.println("creating table [" + TABLE_NAME + "]");

            String createSql = "create table if not exists " + TABLE_NAME + "(" +
                    "date text PRIMARY KEY NOT NULL," +
                    "mood float, " +
                    "story text);";
            try{
                sqLiteDatabase.execSQL(createSql);
            }catch (Exception e){
                System.out.println("Exception in createSql");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            System.out.println("Upgrading database from version " + i + " to " + i1);
        }
    }
}
