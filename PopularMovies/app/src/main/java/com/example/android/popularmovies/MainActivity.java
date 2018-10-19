package com.example.android.popularmovies;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {


    private JSONArray moviesJsonArray;
    private GridView postersGV;
    private final Activity myActivity = (Activity)this;
    private String sortBy;
    private TextView errorMessageTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMessageTV = findViewById(R.id.errorMessageTV);
        postersGV =  findViewById(R.id.gv_posters);
        sortBy = "/popular?";

        makeApiQuery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.moviesmenu, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle() == getResources().getString(R.string.sortByPop)){

         //In this case, the default option (sort by popular) was selected, so we change it to sort by rating.
            item.setTitle(getResources().getString(R.string.sortByRated));
            sortBy = "/top_rated?";


        } else {

         //In this case, sort by rating was already selected so we change it back.
            item.setTitle(getResources().getString(R.string.sortByPop));
            sortBy = "/popular?";


        }
        makeApiQuery();
        return true;
    }
    private void displayErrorMessage(){
        postersGV.setVisibility(View.GONE);
        errorMessageTV.setText(getResources().getString(R.string.errorMsg));
        errorMessageTV.setVisibility(View.VISIBLE);

    }
    private void buildDataSet(JSONArray moviesJA){
        Movie[] movieList = new Movie[moviesJA.length()];
        int i = 0;
        while(i<moviesJA.length()){
            try {
                JSONObject mjob = moviesJA.getJSONObject(i);
                movieList[i] = new Movie(mjob.getString("id"));
                movieList[i].setMoviePosterPath(getString(R.string.poster_directory)+
                        mjob.getString("poster_path"));
                movieList[i].setTitle(mjob.getString("title"));
                movieList[i].setReleaseDate(mjob.getString("release_date"));
                movieList[i].setVoteAvg(mjob.getString("vote_average"));
                movieList[i].setSynopsis(mjob.getString("overview"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        MovieAdapter movieAdapter = new MovieAdapter(myActivity, Arrays.asList(movieList));
        postersGV.setAdapter(movieAdapter);

    }

    private void makeApiQuery() {
            URL ApiQueryUrl = NetworkUtils.buildUrl(sortBy);
            new ApiQueryTask().execute(ApiQueryUrl);
    }

     class ApiQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL ApiQueryUrl = urls[0];
            String ApiQueryResults = null;
            try {
                ApiQueryResults = NetworkUtils.getResponseFromHttpUrl(ApiQueryUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ApiQueryResults;
        }

        @Override
        protected void onPostExecute(String QueryResults) {
            if (QueryResults != null && !QueryResults.equals("")) {
                try {
                    moviesJsonArray = new JSONObject(QueryResults).getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                buildDataSet(moviesJsonArray);
            } else{
                displayErrorMessage();
            }
        }
    }
}
