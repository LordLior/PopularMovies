package com.agiantcookie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.agiantcookie.popularmovies.model.Movie;
import com.agiantcookie.popularmovies.model.MoviesJson;
import com.agiantcookie.popularmovies.utils.Common;
import com.agiantcookie.popularmovies.utils.MovieAdapter;
import com.agiantcookie.popularmovies.utils.MovieAdapter.MovieAdapterOnClickHandler;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
* Tutorial: Parsing JSON on Android using GSON and Volley
* https://kylewbanks.com/blog/tutorial-parsing-json-on-android-using-gson-and-volley
* */

public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private final Response.ErrorListener onMoviesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            closeOnError(R.string.detail_error_message_no_data);

        }
    };
    MoviesJson mMovieJson;
    private String movie_sort_by = Common.DEFAULT_MOVIES_SORT;
    private RecyclerView mRecyclerView;
    private TextView mErrorTextView;
    private MenuItem sortPopularMenuItem;
    private MenuItem sortTopRatedMenuItem;
    private MovieAdapter mMovieAdapter;
    private Gson gson;
    private final Response.Listener<String> onMoviesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            mMovieJson = gson.fromJson(response, MoviesJson.class);

            mMovieAdapter.setMovieData(mMovieJson.getResults());

        }
    };
    private RequestQueue requestQueue;

    private void closeOnError(int errorMessage) {
        finish();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.toolbar, menu);

        sortPopularMenuItem = (MenuItem) menu.findItem(R.id.action_sort_popular);
        sortTopRatedMenuItem = (MenuItem) menu.findItem(R.id.action_sort_top_rated);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_sort_popular:

                if (!movie_sort_by.equals(Common.MOVIE_SORT_BY_POPULAR)) {
                    movie_sort_by = Common.MOVIE_SORT_BY_POPULAR;

                    sortPopularMenuItem.setIcon(R.drawable.ic_radio_button_checked_black_24dp);
                    sortTopRatedMenuItem.setIcon(R.drawable.ic_radio_button_unchecked_black_24dp);

                    fetchMovies(Common.URL_POPULAR);

                }
                return true;
            case R.id.action_sort_top_rated:

                if (!movie_sort_by.equals(Common.MOVIE_SORT_BY_TOP_RATED)) {
                    movie_sort_by = Common.MOVIE_SORT_BY_TOP_RATED;

                    sortPopularMenuItem.setIcon(R.drawable.ic_radio_button_unchecked_black_24dp);
                    sortTopRatedMenuItem.setIcon(R.drawable.ic_radio_button_checked_black_24dp);

                    fetchMovies(Common.URL_TOP_RATED);

                }
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorTextView = (TextView) findViewById(R.id.error_message_tv);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_rv);

        int numberOfColumns = 2;

        GridLayoutManager layoutManager =
                new GridLayoutManager(this, numberOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setDateFormat(Common.API_DATE_FORMAT);
        gson = gsonBuilder.create();

        requestQueue = Volley.newRequestQueue(this);
        fetchMovies(Common.DEFAULT_MOVIES_SORT_URL);
    }

    private void fetchMovies(String movieURL) {

        if (isConnected()) {

            mErrorTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            StringRequest request = new StringRequest(Request.Method.GET, movieURL, onMoviesLoaded, onMoviesError);
            requestQueue.add(request);
        } else {

            mErrorTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }


    }

    public boolean isConnected() {
        // Check for internet connection
        // https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }


    public void onItemClick(View view, Movie movieItem) {
        launchMovieDetailsActivity(movieItem);
    }

    // TODO: Movie intent to CONSTANT
    public void launchMovieDetailsActivity(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(Common.INTENT_MOVIE_DETAILS, movie);
        startActivity(intent);
    }

}

/*
* https://inducesmile.com/android/android-gridlayoutmanager-with-recyclerview-in-material-design/
*
* http://www.thecodecity.com/2017/04/reyclerView-gridlayoutmanager-android-example.html
*
* https://medium.com/@peterekeneeze/parsing-remote-json-to-recyclerview-android-1ad927e96d58
*
* Parcelable in Android
*
* http://www.vogella.com/tutorials/AndroidParcelable/article.html
*
* */



