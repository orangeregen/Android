package com.example.lab2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public static final class DBContract {

        private DBContract() {}

        public static class UserEntry implements BaseColumns {
            private static final String TABLE_NAME = "users_data";
            private static final String COLUMN_ID = "id";
            private static final String COLUMN_LOGIN = "login";
            private static final String COLUMN_PASSWORD = "password";
        }
    }

    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 1;
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + "("
                + DBContract.UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                DBContract.UserEntry.COLUMN_LOGIN + " TEXT," + DBContract.UserEntry.COLUMN_PASSWORD + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }
    public void open() throws SQLException {
        db = getWritableDatabase();
    }

    public void close() {
        if (db != null)
            db.close();
    }

    public long addUser(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBContract.UserEntry.COLUMN_LOGIN, login);
        values.put(DBContract.UserEntry.COLUMN_PASSWORD, password);

        //db.insert(DBContract.UserEntry.TABLE_NAME, null, values);

        return db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
    }

    public List<String> getAllUsers() {
        List<String> usersList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + DBContract.UserEntry.COLUMN_ID + ", " + DBContract.UserEntry.COLUMN_LOGIN + ", "
                + DBContract.UserEntry.COLUMN_PASSWORD + " FROM " + DBContract.UserEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ID));
                @SuppressLint("Range") String login = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_LOGIN));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASSWORD));
                String usersData =  "ID: " + id + " Логин: " + login + " Пароль: " + password;
                usersList.add(usersData);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    public User getUserByUsername(String userlogin) {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = null;

        //String query = "SELECT " + DBContract.UserEntry.COLUMN_LOGIN + ", "
        //        + DBContract.UserEntry.COLUMN_PASSWORD + " FROM " + DBContract.UserEntry.TABLE_NAME;
        //Cursor cursor = db.rawQuery(query, null);

        String[] columns = {DBContract.UserEntry.COLUMN_LOGIN, DBContract.UserEntry.COLUMN_PASSWORD};
        String getLogin = DBContract.UserEntry.COLUMN_LOGIN + " = ?";
        String[] selectionArgs = {userlogin};

        Cursor cursor = db.query(DBContract.UserEntry.TABLE_NAME, columns, getLogin, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String login = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_LOGIN));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASSWORD));
            user = new User(login, password);
            cursor.close();
        }
        return user;
    }

    public void deleteUser(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        String forDelete = DBContract.UserEntry.COLUMN_LOGIN + " = ?";
        String[] selectionArgs = {login};
        db.delete(DBContract.UserEntry.TABLE_NAME, forDelete, selectionArgs);
    }

    public int getCountUser(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {DBContract.UserEntry.COLUMN_ID};
        String selection = DBContract.UserEntry.COLUMN_LOGIN + " = ?" + " AND " + DBContract.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {login, password};

        Cursor cursor = db.query(DBContract.UserEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public boolean checkUser(String login, String password) {
        return getCountUser(login, password) > 0;
    }

    public User changePswd(String login, String oldPswd, String newPswd) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = {DBContract.UserEntry.COLUMN_LOGIN, DBContract.UserEntry.COLUMN_PASSWORD};
        String forChange = DBContract.UserEntry.COLUMN_LOGIN + " = ?" + " AND " + DBContract.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {login, oldPswd};
        Cursor cursor = db.query(DBContract.UserEntry.TABLE_NAME, columns, forChange, selectionArgs, null, null, null);

        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.COLUMN_PASSWORD, newPswd);
        
        db.update(DBContract.UserEntry.TABLE_NAME, values, DBContract.UserEntry.COLUMN_PASSWORD, null);

        cursor.close();
        db.close();
        return null;
    }


    public class User {
        int id;
        String login;
        String password;

        public User(){
        }
        public User(int id, String login, String _password){
            this.id = id;
            this.login = login;
            this.password = _password;
        }
        public User(String login, String _password){
            this.login = login;
            this.password = _password;
        }

        public int getID(){
            return this.id;
        }
        public void setID(int id){
            this.id = id;
        }
        public String getLogin(){
            return this.login;
        }
        public void setLogin(String login){
            this.login = login;
        }
        public String getPassword(){
            return this.password;
        }
        public void setPassword(String _password){
            this.password = _password;
        }
    }
}
