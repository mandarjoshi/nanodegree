package com.gandivainc.myappporfolio.model;

/**
 * This class represents Movie review domain object.
 * Created by ea8b26s on 03/08/2016.
 */
public class MovieReview {

    public static final String REVIEW_URL = "http://api.themoviedb.org/3/movie/";

    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
