package com.agiantcookie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agiantcookie.popularmovies.model.Movie;
import com.agiantcookie.popularmovies.utils.Common;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetailsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError(R.string.detail_error_message_no_data);
        }

        Movie movie = intent.getParcelableExtra(Common.INTENT_MOVIE_DETAILS);

        TextView synopsisLabelTv = (TextView) findViewById(R.id.synopsis_label_tv);
        TextView releaseDateLabelTv = (TextView) findViewById(R.id.release_date_label_tv);
        TextView userRatingLabelTv = (TextView) findViewById(R.id.user_rating_label_tv);

        TextView synopsisTv = (TextView) findViewById(R.id.synopsis_tv);
        TextView releaseDateTv = (TextView) findViewById(R.id.release_date_tv);
        TextView userRatingTv = (TextView) findViewById(R.id.user_rating_tv);

        TextView movieTitleTv = (TextView) findViewById(R.id.movie_details_title_tv);

        String movieTitle = movie.getTitle();

        if (movieTitle.isEmpty()) {
            closeOnError(R.string.detail_error_message_invalid_data);
        } else {
            movieTitleTv.setText(movieTitle);
        }

        ImageView posterIv = (ImageView) findViewById(R.id.movie_details_poster_iv);

        Context imageContext = posterIv.getContext().getApplicationContext();

        String posterThumbnail = Common.IMAGE_BASE_BIG_PATH + movie.getPosterThumbnail();

        if (posterThumbnail.isEmpty()) {
            posterIv.setVisibility(View.GONE);
        } else {
            Glide.with(imageContext)
                    .load(posterThumbnail)
                    .into(posterIv);
            posterIv.setVisibility(View.VISIBLE);
        }

        String synopsis = movie.getSynopsis();

        if (synopsis.isEmpty()) {
            synopsisLabelTv.setVisibility(View.GONE);
            synopsisTv.setVisibility(View.GONE);
        } else {
            synopsisLabelTv.setVisibility(View.VISIBLE);
            synopsisTv.setText(synopsis);
            synopsisTv.setVisibility(View.VISIBLE);
        }

        // Format date
        // https://stackoverflow.com/questions/6637469/converting-date-string-to-a-different-format

        DateFormat fromFormat = new SimpleDateFormat(Common.API_DATE_FORMAT);
        fromFormat.setLenient(false);
        DateFormat toFormat = android.text.format.DateFormat.getDateFormat(this);
        toFormat.setLenient(false);

        String releaseDate = movie.getReleaseDate();

        // trying to set the date
        Date date = null;
        try {
            date = fromFormat.parse(releaseDate);
        } catch (ParseException e) {
            closeOnError(R.string.detail_error_message_invalid_data);
        }

        if (releaseDate.isEmpty()) {
            releaseDateLabelTv.setVisibility(View.GONE);
            releaseDateTv.setVisibility(View.GONE);
        } else {
            releaseDateLabelTv.setVisibility(View.VISIBLE);
            releaseDateTv.setText(toFormat.format(date));
            releaseDateTv.setVisibility(View.VISIBLE);
        }

        Double userRating = movie.getVoteAverage();

        if (userRating.isNaN()) {
            userRatingLabelTv.setVisibility(View.GONE);
            userRatingTv.setVisibility(View.GONE);
        } else {
            userRatingLabelTv.setVisibility(View.VISIBLE);
            userRatingTv.setText(userRating.toString());
            userRatingTv.setVisibility(View.VISIBLE);
        }

    }

    private void closeOnError(int errorMessage) {
        finish();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
