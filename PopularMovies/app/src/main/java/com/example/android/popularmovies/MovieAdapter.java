package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.squareup.picasso.Picasso;



import java.util.List;

/**
 * This class is based on the webcast lesson on Custom ArrayAdapters
 */


class MovieAdapter extends ArrayAdapter<Movie> {

    private final Context mContext;

    public MovieAdapter(Activity context, List<Movie> movies){
        super(context, 0, movies);
        mContext = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.movie_layout, parent, false);
        }

        LinearLayout posterLayout =  convertView.findViewById(R.id.posterLL);
        ImageView poster = convertView.findViewById(R.id.poster);

        poster.setAdjustViewBounds(true);

        //Check to see if the movie actually has a poster
        //If not, assign an imageview resource containing an error message
        if (movie.getPosterPath().contains("null")) {
            Picasso.get()
                    .load(R.drawable.no_img)
                    .resize(this.getContext().getResources().getInteger(R.integer.w185_poster_width),
                            this.getContext().getResources().getInteger(R.integer.w185_poster_height))
                    .into(poster);
            poster.setClickable(false);

        } else {
            //If the poster does exist, use Picasso library to load the picture into an imageview and return it
            Picasso.get()
                    .load(movie.getPosterPath())
                    .resize(this.getContext().getResources().getInteger(R.integer.w185_poster_width),
                            this.getContext().getResources().getInteger(R.integer.w185_poster_height))
                    .into(poster);
            poster.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent startMovieDetailIntent = new Intent(mContext, MovieDetailActivity.class);
                    startMovieDetailIntent.putExtra("Movie", movie);
                   mContext.startActivity(startMovieDetailIntent);
                }
            });
        }

        return convertView;

    }
}
