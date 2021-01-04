package com.getsoft.dicolega;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String DATABASE_NAME = "db_dicolega.db";
    public static final int DATABASE_VERSION = 1;

    private String DATABASE_LOCATION = "";
    private String DATABASE_FULL_PATH = "";

    private final String TBL_FR_KILEG = "fra_kilega";
    private final String TBL_KILEG_KILEG = "kileg_kileg";
    private final String TBL_ANGL_KILEG = "angl_kileg";
    private final String TBL_BOOKMARK = "bookmark";

    private final String COL_KEY = "key";
    private final String COL_VALUE = "value";

    public SQLiteDatabase mDB;

    public DBHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
        mContext = context;

        DATABASE_LOCATION = "data/data/"+mContext.getPackageName()+"/database/";
        DATABASE_FULL_PATH = DATABASE_LOCATION + DATABASE_NAME;

        if (!isExistingDB()){
            try {
                // Create directory before copy database
                File dbLocation = new File(DATABASE_LOCATION);
                dbLocation.mkdir();

                extractAssetToDatabaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);


    }

    boolean isExistingDB(){
        File file = new File(DATABASE_FULL_PATH);
        return file.exists();
    }

    public void extractAssetToDatabaseDirectory(String fileName) throws IOException {
        int length = 0;
        InputStream sourceDatabase = this.mContext.getAssets().open(fileName);
        File destinationPath = new File(DATABASE_FULL_PATH);
        OutputStream destination = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[4096];
        while ((length = sourceDatabase.read(buffer)) > 0){
            destination.write(buffer,0,length);
        }

        sourceDatabase.close();
        destination.flush();
        destination.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE fran_kile ("
                + " keys VARCHAR primary key,"
                + " value TEXT NOT NULL"
                + ")";
        db.execSQL(sql1);

        String sql = "CREATE TABLE bookmark ("
                + " keys TEXT(100) primary key,"
                + " tables TEXT(10) NOT NULL,"
                + " date DATETIME NOT NULL"
                + ")";
        db.execSQL(sql);

        String sql2 = "CREATE TABLE kile_kile ("
                + " keys VARCHAR primary key,"
                + " value TEXT NOT NULL"
                + ")";
        db.execSQL(sql2);

        String sql3 = "CREATE TABLE ang_kile ("
                + " keys VARCHAR primary key,"
                + " value TEXT NOT NULL"
                + ")";
        db.execSQL(sql3);
        Log.i("DATABASE", "onCreate: ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertMotsFran_Kile(String ky, String vl){
        ky = ky.replace("'", "''");
        String sql = "INSERT INTO fran_kile(keys, value) VALUES('"
                + ky + "', '" + vl + "')";
        //this.getWritableDatabase().execSQL(sql);
        Log.i("DATABASE", "insertMotsFran_Kile: ");
    }

    public ArrayList<String> getWord(int dicType){
        String tableName = getTableName(dicType);
        String sql = "SELECT * FROM " + tableName;
        Cursor result = mDB.rawQuery(sql, null);

        ArrayList<String> source = new ArrayList<>();
        while (result.moveToNext()){
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }

        return source;
    }

    public Word getWord(String key, int dicType){
        String tableName = getTableName(dicType);
        String sql = "SELECT * FROM " + tableName + " WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(sql, new String[]{key});

        Word word = new Word();
        while (result.moveToNext()){
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));
        }

        return word;
    }

    public void addBookmark(Word word){
        try {
            String q = "INSERT INTO bookmark(["+ COL_KEY +"],["+ COL_VALUE +"]) VALUES (?,?); ";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public void removeBookmark(Word word){
        try {
            String q = "DELETE FROM bookmark WHERE upper(["+ COL_KEY +"] = upper(?) AND ["+ COL_VALUE +"]) = ?; ";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public ArrayList<String> getAllWordFromBookmark(String key){

        String sql = "SELECT * FROM bookmark ORDER BY [date] DESC;";
        Cursor result = mDB.rawQuery(sql, new String[]{key});

        ArrayList<String> source = new ArrayList<>();
        while (result.moveToNext()){
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }

        return source;
    }

    public boolean isWordMark(Word word){
        String sql = "SELECT * FROM bookmark WHERE upper([key]) = upper(?) AND [value] = ?";
        Cursor result = mDB.rawQuery(sql, new String[]{word.key, word.value});
        return result.getCount() > 0;
    }

    public Word getWordFromBookmark(String key){
        String sql = "SELECT * FROM bookmark WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(sql, new String[]{key});

        Word word = null;
        while (result.moveToNext()){
            word = new Word();
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;
    }

    public String getTableName(int dicType){
        String tableName = "";
        if (dicType == R.id.action_fr_kil){
            tableName = TBL_FR_KILEG;
        }else if (dicType == R.id.action_kil_fr){
            tableName = TBL_KILEG_KILEG;
        }else if (dicType == R.id.action_ang_kil){
            tableName = TBL_ANGL_KILEG;
        }

        return tableName;
    }

}
