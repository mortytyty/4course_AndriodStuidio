package com.example.lb02.Handlers;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_KEY_ID = "id";
        public static final String COLUMN_NAME_FIRSTNAME = "first_name";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASS = "password";
    }
}
