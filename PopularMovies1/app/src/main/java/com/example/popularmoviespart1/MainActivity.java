package com.example.popularmoviespart1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviespart1.model.Movie;
import com.example.popularmoviespart1.utilities.JsonUtils;
import com.example.popularmoviespart1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private final String POPULAR = "popular";
    private final String RATING = "rated";
    private final String DEFAULT = "default";
    private RecyclerView mMovieRecyclerView;
    private TextView mErrorTextView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private final String choiceKey = "CHOICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRecyclerView = findViewById(R.id.my_recyler_view);
        mErrorTextView = findViewById(R.id.error_text_view);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mMovieRecyclerView.setHasFixedSize(true);

        int NUMBER_OF_COLUMNS = 2;
        RecyclerView.LayoutManager layoutManager = (new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        mMovieRecyclerView.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mMovieRecyclerView.setAdapter(mMovieAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",
                MODE_PRIVATE);

        String choice = sharedPreferences.getString(choiceKey, DEFAULT);

        loadMovieData(choice);

    }

    private void showError(){
        mErrorTextView.setVisibility(View.VISIBLE);
        mMovieRecyclerView.setVisibility(View.INVISIBLE);

    }

    private void showRecyclerView(){
        mMovieRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie currentMovie) {
        Bundle extras = new Bundle();
        extras.putString(getString(R.string.title), currentMovie.getTitle());
        extras.putString(getString(R.string.posterpath), currentMovie.getPosterPath());
        extras.putString(getString(R.string.synopsis), currentMovie.getSynopsis());
        extras.putString(getString(R.string.user_rating), currentMovie.getUserRating());
        extras.putString(getString(R.string.release_date), currentMovie.getReleaseDate());
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtras(extras);
        startActivity(intentToStartDetailActivity);
    }

    @SuppressLint("StaticFieldLeak")
    class FetchMovieTask extends AsyncTask<String, Void, Movie[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param sort_param The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Movie[] doInBackground(String... sort_param) {
            URL url = null;
            List<Movie> movieList = null;
            switch (sort_param[0]) {
                case POPULAR:
                    //load popular movies
                    url = NetworkUtils.buildPopularUrl();
                    break;
                case RATING:
                    url = NetworkUtils.buildRatedUrl();
                    break;
                case DEFAULT:
                    url = NetworkUtils.buildDefaultUrl();
                    break;
            }

            try {
                assert url != null;
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(url);
                if (jsonMovieResponse != null){
                    movieList = JsonUtils.parseMovieJson(jsonMovieResponse);
                }
                if (movieList == null){
                    return null;
                }else{
                    return movieList.toArray(new Movie[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null){
                showRecyclerView();
                mMovieAdapter.setMovieData(movies);
            }else {
                showError();
            }


        }
    }


    private void loadMovieData(String option){
        showRecyclerView();
        new FetchMovieTask().execute(option);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.selector_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        String mCurrentChoice;
        if (id == R.id.action_sort_popular) {
            mCurrentChoice = POPULAR;
            myEdit.putString(choiceKey, mCurrentChoice);
            myEdit.apply();
            loadMovieData(POPULAR);
            return true;
        }

        if(id == R.id.action_sort_rating){
            mCurrentChoice = RATING;
            myEdit.putString(choiceKey, mCurrentChoice);
            myEdit.apply();
            loadMovieData(RATING);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
