package com.gandivainc.myappporfolio.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gandivainc.myappporfolio.model.Movie;

/**
 * This Class is responsible for maintaining database schema for application.
 * Created by ea8b26s on 04/20/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Movie.db";

    //scripts
    private static final String CREATE_TABLE_MOVIE = "CREATE TABLE " + Movie.FAVOURITE_MOVIE_TABLENAME
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + Movie.COLUMN_MOVIE_ID + " INTEGER,"
            + Movie.COLUMN_TITLE + " TEXT,"
            + Movie.COLUMN_RELEASE_DATE + " TEXT,"
            + Movie.COLUMN_OVERVIEW + " TEXT,"
            + Movie.COLUMN_POSTER_PATH + " TEXT,"
            + Movie.COLUMN_VOTE_AVERAGE + " TEXT)";

    private static final String DROP_TABLE_MOVIE = "DROP TABLE IF EXISTS " + Movie.FAVOURITE_MOVIE_TABLENAME;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MovieDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createSchema(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cleanSchema(db);
        createSchema(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        cleanSchema(db);
        createSchema(db);
    }

    private void createSchema(SQLiteDatabase db) {
        cleanSchema(db);
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    private void cleanSchema(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE_MOVIE);
    }
}
