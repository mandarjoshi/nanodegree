package com.gandivainc.myappporfolio.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.gandivainc.myappporfolio.db.MovieDbHelper;
import com.gandivainc.myappporfolio.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for implementing CRUD operations for Movie table in local database.
 * Created by ea8b26s on 04/21/2016.
 */
public class MovieProvider extends ContentProvider {

    private static final String AUTHORITY = "com.gandivainc.myappporfolio.provider.MovieProvider";
    private static final int ALL_MOVIES = 1;
    private static final int SINGLE_MOVIE = 2;
    private static final String MOVIES_BASE_PATH = Movie.FAVOURITE_MOVIE_TABLENAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + MOVIES_BASE_PATH);
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, Movie.FAVOURITE_MOVIE_TABLENAME, ALL_MOVIES);
        uriMatcher.addURI(AUTHORITY, Movie.FAVOURITE_MOVIE_TABLENAME + "/#", SINGLE_MOVIE);
    }

    Context context;
    MovieDbHelper movieDbHelper;

    public MovieProvider(Context context) {
        this.context = context;
        movieDbHelper = new MovieDbHelper(this.context);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_MOVIES:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Movie.FAVOURITE_MOVIE_TABLENAME;
            case SINGLE_MOVIE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Movie.FAVOURITE_MOVIE_TABLENAME;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_MOVIES:
                //do nothing
                break;
            case SINGLE_MOVIE:
                String id = uri.getPathSegments().get(1);
                selection = Movie.COLUMN_MOVIE_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(Movie.FAVOURITE_MOVIE_TABLENAME, values, selection, selectionArgs);
        context.getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        long id = db.insert(Movie.FAVOURITE_MOVIE_TABLENAME, null, values);
        context.getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(this.context);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_MOVIES:
                //do nothing
                break;
            case SINGLE_MOVIE:
                String id = uri.getPathSegments().get(1);
                selection = Movie.COLUMN_MOVIE_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(Movie.FAVOURITE_MOVIE_TABLENAME, selection, selectionArgs);
        context.getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Movie.FAVOURITE_MOVIE_TABLENAME);

        switch (uriMatcher.match(uri)) {
            case ALL_MOVIES:
                //do nothing
                break;
            case SINGLE_MOVIE:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(Movie.COLUMN_MOVIE_ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }

    /**
     * This method is responsible for adding favourite movie.
     * @param movie
     * @return
     */
    public boolean addFavouriteMovie(Movie movie) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Movie.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(Movie.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(Movie.COLUMN_POSTER_PATH, movie.getPoster_path());
        contentValues.put(Movie.COLUMN_RELEASE_DATE, movie.getRelease_date());
        contentValues.put(Movie.COLUMN_TITLE, movie.getTitle());
        contentValues.put(Movie.COLUMN_VOTE_AVERAGE, movie.getVote_average());

        Uri result = insert(CONTENT_URI, contentValues);
        return result != null;
    }

    /**
     * This method is responsible for removing as favourite
     * @param id
     * @return
     */
    public boolean removeFavouriteMovie(int id) {
        int result = delete(Uri.parse(CONTENT_URI + "/" + id), null, null);
        return result > 0;
    }

    /**
     * This method is responsible to check if selected movie is already marked as favourite.
     * @param id
     * @return
     */
    public boolean isMovieFavourite(int id) {
        SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        String whereClausse = Movie.COLUMN_MOVIE_ID + " = " + id;
        Cursor cursor = query(Uri.parse(CONTENT_URI + "/" + id), new String[]{Movie.COLUMN_MOVIE_ID}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is responsible for getting all favourite movies.
     * @return
     */
    public List<Movie> getFavouriteMovies() {
        SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        Cursor cursor = query(CONTENT_URI, new String[]{Movie.COLUMN_MOVIE_ID, Movie.COLUMN_OVERVIEW, Movie.COLUMN_POSTER_PATH, Movie.COLUMN_RELEASE_DATE, Movie.COLUMN_TITLE, Movie.COLUMN_VOTE_AVERAGE},
                null, null, "ID DESC");
        List<Movie> movies = new ArrayList<Movie>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_MOVIE_ID)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_OVERVIEW)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_POSTER_PATH)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_RELEASE_DATE)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)));
                movie.setVote_average(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_VOTE_AVERAGE)));
                movies.add(movie);
            }
        }
        return movies;
    }

}
