package com.agiantcookie.popularmovies.model;

import java.util.List;

public class MoviesJson {

    private int page;
    private List<Movie> results;

    public MoviesJson(int page, List<Movie> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
