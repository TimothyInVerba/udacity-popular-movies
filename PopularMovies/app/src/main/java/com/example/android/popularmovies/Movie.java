package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 5/13/18.
 */

public class Movie implements Parcelable {

    private String moviePosterPath;
    private String ID;
    private String title;
    private String releaseDate;
    private String synopsis;
    private String voteAvg;

    /*
     *The Following is for Parcelable interface to work properly.
     */

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel parc) {
            return new Movie(parc);
        }
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

    @Override
    public int describeContents() { return 0; }

    //This is a constructor for parcelable to work correctly.
    private Movie(Parcel parc){
        title = parc.readString();
        releaseDate = parc.readString();
        ID = parc.readString();
        synopsis = parc.readString();
        moviePosterPath = parc.readString();
        voteAvg = parc.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(ID);
        parcel.writeString(synopsis);
        parcel.writeString(moviePosterPath);
        parcel.writeString(voteAvg);
    }

//Constructor for Movie, just sets ID
    public Movie(String id){ ID = id; }


//Getter Methods
    public String getSynopsis(){ return synopsis; }
    public String getReleaseDate(){ return releaseDate; }
    public String getVoteAvg() { return voteAvg; }
    public String getPosterPath(){ return moviePosterPath; }
    public String getID() { return ID; }
    public String getTitle(){ return title; }
//Setter Methods
    public void setMoviePosterPath(String mpp){ moviePosterPath = mpp; }
    public void setTitle(String t){ title = t; }
    public void setID(String id){ ID = id; }
    public void setReleaseDate(String rd) { releaseDate = rd; }
    public void setSynopsis(String s) { synopsis = s; }
    public void setVoteAvg(String va) { voteAvg = va; }
}
