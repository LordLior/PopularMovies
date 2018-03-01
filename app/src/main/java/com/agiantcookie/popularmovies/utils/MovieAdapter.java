package com.agiantcookie.popularmovies.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.agiantcookie.popularmovies.R;
import com.agiantcookie.popularmovies.model.Movie;
import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static MovieAdapterOnClickHandler mClickHandler;
    private static List<Movie> mMoviesData;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        //setMovieData(moviesData);
    }

    public void setMovieData(List<Movie> moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }

    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_tile;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMoviesData.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.movie_tile;
    }

    @Override
    public int getItemCount() {
        if (null == mMoviesData) return 0;
        return mMoviesData.size();
    }

    public interface MovieAdapterOnClickHandler {
        void onItemClick(View view, Movie movieItem);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private final TextView mTextView;
        private final ImageView mImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_poster_iv);
            mTextView = (TextView) itemView.findViewById(R.id.movie_title_tv);

            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {

            mTextView.setText(movie.getTitle());

            Context imageContext = mImageView.getContext().getApplicationContext();

            Glide.with(imageContext)
                    .load(Common.IMAGE_BASE_PATH + movie.getPosterThumbnail())
                    .fitCenter()
                    .centerCrop()
                    .placeholder(R.drawable.movie_poster_loading)
                    .error(R.drawable.movie_poster_error)
                    .override(185, 277)
                    .into(mImageView);
        }

        @Override
        public void onClick(View itemView) {
            int adapterPosition = getAdapterPosition();
            Movie movieItem = mMoviesData.get(adapterPosition);
            mClickHandler.onItemClick(itemView, movieItem);
        }
    }

}
