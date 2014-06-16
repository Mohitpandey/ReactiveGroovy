package com.mpandey.reactivegroovy

import groovy.transform.CompileStatic

import java.io.Serializable;
import java.util.List;

@CompileStatic
public class Movie implements Serializable {

    private static final long serialVersionUID = 8359220892008313080L;

    private String title;
    private Poster posters;
    private String synopsis;

    public String getSynopsis() {
        return synopsis;
    }

    public Movie() {
    }

    private long id;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Poster getPosters() {
        return posters;
    }
}

class Poster implements Serializable {

    private static final long serialVersionUID = 8359220892008313082L;
    private String thumbnail;
    private String profile;

    public String getOriginal() {
        return original;
    }

    private String original;

    public String getProfile() {
        return profile;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}

class Movies implements Serializable {
    private static final long serialVersionUID = 8359220892008313083L;
    List<Movie> movies;
}
