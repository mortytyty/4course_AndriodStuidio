package com.example.lb02.Handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lb02.Models.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
                    getTableLog();
                    dataBase.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        dataBase.close();
        getTableLog();
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
                if (Objects.equals(cursor.getString(0), login) && Objects.equals(cursor.getString(1), pass)){
                    dataBase.close();
                    getTableLog();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        dataBase.close();
        getTableLog();
        return false;
    }
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.COLUMN_NAME_FIRSTNAME, user.getFirstName());
        values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());

        long newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }
    public int removeUser(String login){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DBContract.UserEntry.COLUMN_NAME_LOGIN + " LIKE ?";
        String[] selectionArgs = { login };
        int deletedRows = db.delete(DBContract.UserEntry.TABLE_NAME,selection ,selectionArgs);
        db.close();
        getTableLog();
        return deletedRows;
    }
    public int changePassword(String login, String newPass){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, newPass);

        String selection = DBContract.UserEntry.COLUMN_NAME_LOGIN + " LIKE ?";
        String[] selectionArgs = { login };

        int count = db.update(
                DBContract.UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
        getTableLog();
        return count;
    }
    public String getName(String login){
        String selectQuery = "SELECT  "+DBContract.UserEntry.COLUMN_NAME_FIRSTNAME+", "+DBContract.UserEntry.COLUMN_NAME_LOGIN+" FROM " + DBContract.UserEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if (Objects.equals(cursor.getString(1), login))
                {
                    db.close();
                    getTableLog();
                    return cursor.getString(0);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        getTableLog();
        return "empty *-*";
    }
    public void getTableLog() {

        String selectQuery = "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();

        String key="DataBase (version "+dtf.format(localTime)+")";
        String row;
        if (cursor.moveToFirst()) {
            do {
                row =   "ID: "+cursor.getString(0)+", "
                        +"FirstName: "+cursor.getString(1)+", "
                        +"Login: "+cursor.getString(2)+", "
                        +"Pass: "+cursor.getString(3);
                Log.d(key,row);
            } while (cursor.moveToNext());
        }
        db.close();
    }
}
