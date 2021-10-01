package com.example.popularmoviespart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmoviespart1.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static final String RATING_TOTAL = "/10";

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

    private TextView mMovieTitleView;
    private TextView mUserRatingView;
    private TextView mReleaseDateView;
    private TextView mSynopsisView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitleView = findViewById(R.id.movie_title);
        ImageView mImageView = findViewById(R.id.image_box);
        mUserRatingView = findViewById(R.id.user_rating);
        mReleaseDateView = findViewById(R.id.release_date);
        mSynopsisView = findViewById(R.id.synopsis);

        Intent intent = getIntent();
        if (intent == null) {
           closeOnError();
        }else{

            Bundle extras = intent.getExtras();

            if (extras != null) {
                Movie movieDetail = new Movie();
                movieDetail.setTitle(extras.getString(getString(R.string.title)));
                movieDetail.setPosterPath(extras.getString(getString(R.string.posterpath)));
                movieDetail.setSynopsis(extras.getString(getString(R.string.synopsis)));
                movieDetail.setUserRating(extras.getString(getString(R.string.user_rating)));
                movieDetail.setReleaseDate(extras.getString(getString(R.string.release_date)));
                populateDetailUI(movieDetail);

                String getPath = movieDetail.getPosterPath();
                String fullPath = IMAGE_BASE_URL + getPath;



                Picasso.get()
                        .load(fullPath)
                        .placeholder(R.mipmap.placeholder_image)
                        .error(R.mipmap.placeholder_error_image)
                        .resize(WIDTH, HEIGHT)
                        .into(mImageView);
            }
        }
    }

    private void populateDetailUI(Movie movieDetail) {
        mMovieTitleView.setText(movieDetail.getTitle());
        mSynopsisView.setText(movieDetail.getSynopsis());
        mReleaseDateView.setText(movieDetail.getReleaseDate().substring(0,4));
        mUserRatingView.setText(String.format("%s%s", movieDetail.getUserRating(), RATING_TOTAL));
    }

    private void closeOnError(){
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
