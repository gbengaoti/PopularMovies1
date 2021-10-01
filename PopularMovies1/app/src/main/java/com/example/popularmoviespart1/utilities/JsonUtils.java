package com.example.popularmoviespart1.utilities;

import com.example.popularmoviespart1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String SYNOPSIS = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String RESULTS = "results";
    private static final String NOT_FOUND = "N/A";

    public static List<Movie> parseMovieJson(String json) {
        List<Movie> movieList = new ArrayList<>();
        /* contains list of movies */


        try {
            JSONObject jsonObject = new JSONObject(json);
            // get list of results
            JSONArray resultList = (JSONArray) jsonObject.get(RESULTS);
            for(int i=0; i < resultList.length(); i++) {
                Movie myMovie = new Movie();
                JSONObject movieObject = resultList.getJSONObject(i);
                if (movieObject.has(TITLE)){
                    String title = movieObject.get(TITLE).toString();
                    myMovie.setTitle(title);
                }else{
                    myMovie.setTitle(NOT_FOUND);
                }

                if (movieObject.has(POSTER_PATH)){
                    String posterPath = movieObject.get(POSTER_PATH).toString();
                    myMovie.setPosterPath(posterPath);
                }else{
                    myMovie.setPosterPath(NOT_FOUND);
                }

                if (movieObject.has(SYNOPSIS)){
                    String synopsis = movieObject.get(SYNOPSIS).toString();
                    myMovie.setSynopsis(synopsis);
                }else{
                    myMovie.setSynopsis(NOT_FOUND);
                }

                if (movieObject.has(USER_RATING)){
                    String userRating = movieObject.get(USER_RATING).toString();
                    myMovie.setUserRating(userRating);
                }else{
                    myMovie.setUserRating(NOT_FOUND);
                }

                if (movieObject.has(RELEASE_DATE)){
                    String releaseDate = movieObject.get(RELEASE_DATE).toString();
                    myMovie.setReleaseDate(releaseDate);
                }else{
                    myMovie.setReleaseDate(NOT_FOUND);
                }

                movieList.add(myMovie);
            }
            return movieList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
