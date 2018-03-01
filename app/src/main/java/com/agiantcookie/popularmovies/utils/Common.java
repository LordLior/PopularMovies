package com.agiantcookie.popularmovies.utils;

import com.agiantcookie.popularmovies.BuildConfig;

public class Common {

    public static final String INTENT_MOVIE_DETAILS = "Movie";
    public static final String MOVIE_SORT_BY_POPULAR = "popular";
    public static final String MOVIE_SORT_BY_TOP_RATED = "top_rated";
    public static final String DEFAULT_MOVIES_SORT = MOVIE_SORT_BY_POPULAR;
    public static final String DEFAULT_MOVIES_SORT_URL = URL_POPULAR;
    public static final String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w185";
    public static final String IMAGE_BASE_BIG_PATH = "http://image.tmdb.org/t/p/w780";
    public static final String API_KEY_ID = "id";
    public static final String API_KEY_ORIGINAL_TITLE = "original_title";
    public static final String API_KEY_POSTER_PATH = "poster_path";
    public static final String API_KEY_SYNOPSIS = "overview";
    public static final String API_KEY_VOTE_AVERAGE = "vote_average";
    public static final String API_KEY_RELEASE_DATE = "release_date";
    public static final String API_KEY_BACK_DROP = "backdrop_path";
    public static final String API_DATE_FORMAT = "yyyy-M-dd";
    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;
    public static final String URL_POPULAR = "https://api.themoviedb.org/3/movie/" + Common.MOVIE_SORT_BY_POPULAR + "?api_key=" + MOVIES_API_KEY;
    public static final String URL_TOP_RATED = "https://api.themoviedb.org/3/movie/" + Common.MOVIE_SORT_BY_TOP_RATED + "?api_key=" + MOVIES_API_KEY;

}
