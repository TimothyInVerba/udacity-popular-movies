package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movieForDetails;
        ImageView detailsPoster = findViewById(R.id.detailedPosterIV);
        TextView detailsTitleTV = findViewById(R.id.title);
        TextView detailsReleaseDateTV = findViewById(R.id.release_date);
        TextView detailsVoteAvgTV = findViewById(R.id.vote_average);
        TextView detailsSynopsisTV = findViewById(R.id.plot_synopsis);

        detailsPoster.setAdjustViewBounds(true);

        Intent intentThatStartedThis = getIntent();
        if(intentThatStartedThis.hasExtra("Movie")){
            movieForDetails = intentThatStartedThis.getParcelableExtra("Movie");
        } else {
            //error
            movieForDetails = new Movie("123");
        }

        Picasso.get()
                    .load(movieForDetails.getPosterPath())
                    .resize(this.getResources().getInteger(R.integer.w185_poster_width),
                            this.getResources().getInteger(R.integer.w185_poster_height))
                    .into(detailsPoster);
        String releaseDate = getResources().getString(R.string.releaseDate)
                            + movieForDetails.getVoteAvg();
        String voteAvg = getResources().getString(R.string.voteAvg)
                        + movieForDetails.getReleaseDate();

        detailsTitleTV.setText(movieForDetails.getTitle());
        detailsVoteAvgTV.setText(voteAvg);
        detailsReleaseDateTV.setText(releaseDate);
        detailsSynopsisTV.setText(movieForDetails.getSynopsis());
    }
}
