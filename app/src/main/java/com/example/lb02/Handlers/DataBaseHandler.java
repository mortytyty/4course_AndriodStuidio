package com.example.lb02.Handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lb02.Models.User;

import java.util.Objects;


public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE =
            "CREATE TABLE " +
                DBContract.UserEntry.TABLE_NAME +
                "(" +
                    DBContract.UserEntry.COLUMN_NAME_KEY_ID + " INTEGER PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_NAME_FIRSTNAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_NAME_LOGIN + " TEXT," +
                    DBContract.UserEntry.COLUMN_NAME_PASS + " TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean checkUserLoginExist(String login) {
        SQLiteDatabase dataBase = this.getReadableDatabase();

        String selectQuery =
                "SELECT  " +
                        DBContract.UserEntry.COLUMN_NAME_LOGIN +
                            " FROM " +
                            DBContract.UserEntry.TABLE_NAME;

        Cursor cursor = dataBase.rawQuery(selectQuery, null);

        Log.d("DataBaseOut","CheckExist");

        if (cursor.moveToFirst()) {
            do {
                //Log.d("DataBaseOut","Login: "+cursor.getString(0)+" "+login);
                if (Objects.equals(cursor.getString(0), login)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }
    public boolean checkDataValidation(String login,String pass){
        SQLiteDatabase dataBase = this.getReadableDatabase();

        String selectQuery =
                "SELECT  " +
                    DBContract.UserEntry.COLUMN_NAME_LOGIN +
                    ", " +
                    DBContract.UserEntry.COLUMN_NAME_PASS +
                        " FROM " +
                        DBContract.UserEntry.TABLE_NAME;

        Cursor cursor = dataBase.rawQuery(selectQuery, null);

        Log.d("DataBaseOut","CheckValid");

        if (cursor.moveToFirst()) {
            do {
                //Log.d("DataBaseOut","Login: "+cursor.getString(0)+" "+login);
                //Log.d("DataBaseOut","Pass: "+cursor.getString(1)+" "+pass);
                if (Objects.equals(cursor.getString(0), login) && Objects.equals(cursor.getString(1), pass)){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.COLUMN_NAME_FIRSTNAME, user.getFirstName());
        values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());

        db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void removeUser(String login){

    }
    public void changePassword(User user, String newPass){

    }
    public void getTableLog() {

        String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Log.d("DataBaseList",cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3));
            } while (cursor.moveToNext());
        }
    }

}
