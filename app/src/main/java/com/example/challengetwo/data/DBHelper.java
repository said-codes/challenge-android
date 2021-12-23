package com.example.challengetwo.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;


    public DBHelper(Context context) {
        super(context, "challenge_three.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE PRODUCTS ( " +
                "id Integer PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "description VARCHAR ," +
                "price VARCHAR," +
                "image BLOB)");

        sqLiteDatabase.execSQL("CREATE TABLE SERVICES ( " +
                "id Integer PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "description VARCHAR ," +
                "price VARCHAR," +
                "image BLOB)");

        sqLiteDatabase.execSQL("CREATE TABLE OFFICES ( " +
                "id Integer PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "description VARCHAR ," +
                "location VARCHAR," +
                "image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SERVICES");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS OFFICES");
    }

    //OWN METHODS
    public void insertData(String field1, String field2, String field3,byte [] image, String table){
        String sql = "INSERT INTO " +table+" VALUES(null, ?,?,?,?)";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);

        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1,field1);
        sqLiteStatement.bindString(2,field2);
        sqLiteStatement.bindString(3,field3);
        sqLiteStatement.bindBlob(4,image);

        sqLiteStatement.executeInsert();
    }
    public Cursor getData(String table){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +table,null);
        return cursor;
    }
    public Cursor getDataById(String table, String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +table +" WHERE id = " + id,null);
        return cursor;
    }
    public void deleteById(String table, String id){
        sqLiteDatabase.execSQL(" DELETE FROM " + table+ " WHERE id = " + id);
    }
    public void updateTableById(String table,String id, String field1,String field2,String field3, byte[] img){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",field1);
        contentValues.put("description",field2);
        contentValues.put("price",field3);
        sqLiteDatabase.update(table,contentValues,"id = ?", new String[]{id});
    }

    public void updateOfficeById(String table,String id, String field1,String field2,String field3, byte[] img){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",field1);
        contentValues.put("description",field2);
        contentValues.put("location",field3);
        sqLiteDatabase.update(table,contentValues,"id = ?", new String[]{id});
    }

}
