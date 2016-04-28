package com.gandivainc.myappporfolio.model;

import android.net.Uri;

import java.util.List;

/**
 * This class represents Movie domain object.
 * Created by ea8b26s on 02/23/2016.
 */
public class Movie implements IModel {

    public static final String API_KEY = "951dbb17b4e3480364e9232c49c57f1c";
    public static final String YOUTUBE_API_KEY = "AIzaSyBvi44ajDJ9ylyCtfqvM2RHePg61v5d6ec";
    public static final String URL_BASE_MOVIE_POSTER = "http://image.tmdb.org/t/p/w185";
    public static final String COLUMN_MOVIE_ID = "MOVIE_ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_RELEASE_DATE = "RELEASE_DATE";
    public static final String COLUMN_OVERVIEW = "OVERVIEW";
    public static final String COLUMN_POSTER_PATH = "POSTER_PATH";
    public static final String COLUMN_VOTE_AVERAGE = "VOTE_AVERAGE";
    public static final String FAVOURITE_MOVIE_TABLENAME = "FAVOURITE_MOVIE";
    public static String MODEL_NAME = "MOVIE";
    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private String popularity;
    private String vote_count;
    private boolean video;
    private String vote_average;

    public static String getMovieListURL(int page, String sortedBy) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("sort_by", sortedBy)
                .appendQueryParameter("page", "" + page);
        return builder.build().toString();
    }

    public static String getMovieVideoURL(int id) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + id)
                .appendPath("videos")
                .appendQueryParameter("api_key", API_KEY);
        return builder.build().toString();
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }
}