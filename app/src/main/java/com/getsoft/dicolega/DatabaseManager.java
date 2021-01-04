package com.getsoft.dicolega;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_dicolega.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE fran_kile ("
//                   + " Idfk integer primary key autoincrement,"
//                   + " keys TEXT NOT NULL,"
//                   + " valeurs integer NOT NULL"
//                   + ")";
        String sql = "CREATE TABLE fran_kile ("
                + " Idfk integer primary key autoincrement,"
                + " keys VARCHAR(100),"
                + " value TEXT NOT NULL"
                + ")";
        db.execSQL(sql);
        Log.i("DATABASE", "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE fran_kile";
        db.execSQL(sql);
        this.onCreate(db);
        Log.i("DATABASE", "onUpgrade: ");
    }

    public void insertMots(String ky, String vl){
        ky = ky.replace("'", "''");
        String sql = "INSERT INTO fran_kile(keys, value) VALUES('"
                + ky + "', '" + vl + "')";
        this.getWritableDatabase().execSQL(sql);
        Log.i("DATABASE", "insertMots: ");
    }

}
