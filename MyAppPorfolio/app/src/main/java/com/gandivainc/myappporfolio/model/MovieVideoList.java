package com.gandivainc.myappporfolio.model;

import java.util.List;

/**
 * This class represents Movie Video List domain object mapped to service return in JSON format.
 * Created by ea8b26s on 03/08/2016.
 */
public class MovieVideoList {
    private int id;
    private List<MovieVideo> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieVideo> getResults() {
        return results;
    }

    public void setResults(List<MovieVideo> results) {
        this.results = results;
    }
}
