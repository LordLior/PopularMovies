package com.agiantcookie.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.agiantcookie.popularmovies.utils.Common;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            try {
                return new Movie(in);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName(Common.API_KEY_ID)
    public long id;
    @SerializedName(Common.API_KEY_ORIGINAL_TITLE)
    public String title;
    @SerializedName(Common.API_KEY_POSTER_PATH)
    public String posterThumbnail;
    @SerializedName(Common.API_KEY_SYNOPSIS)
    public String synopsis;
    @SerializedName(Common.API_KEY_VOTE_AVERAGE)
    public double voteAverage;
    @SerializedName(Common.API_KEY_RELEASE_DATE)
    public String releaseDate;
    @SerializedName(Common.API_KEY_BACK_DROP)
    public String backDrop;

    public Movie(long id, String title, String posterThumbnail, String synopsis, double voteAverage, String releaseDate, String backDrop) {
        this.id = id;
        this.title = title;
        this.posterThumbnail = posterThumbnail;
        this.synopsis = synopsis;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.backDrop = backDrop;
    }

    protected Movie(Parcel in) throws ParseException {
        id = in.readLong();
        title = in.readString();
        posterThumbnail = in.readString();
        synopsis = in.readString();
        voteAverage = in.readDouble();
        releaseDate = in.readString();
        backDrop = in.readString();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterThumbnail() {
        return posterThumbnail;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackDrop() {
        return backDrop;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(posterThumbnail);
        dest.writeString(synopsis);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(backDrop);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}