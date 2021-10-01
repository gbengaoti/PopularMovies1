package com.example.popularmoviespart1.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String DEFAULT_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
    private static final String POPULAR_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
    private static final String RATED_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?";
    private final static String API_KEY_PARAM = "api_key";
    private final static String API_KEY = "dc1ce2ef3b3b2331de717318ffbd5b1f";
    private final static String SORT_BY_PARAM = "sort_by";
    private final static String SORT_BY_KEY = "original_title.asc";

    public static URL buildDefaultUrl() {
        Uri builtUri = Uri.parse(DEFAULT_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(SORT_BY_PARAM, SORT_BY_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildRatedUrl() {
        Uri builtUri = Uri.parse(RATED_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildPopularUrl() {
        Uri builtUri = Uri.parse(POPULAR_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /*Copied from S04.03 sunshine homework */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }





}
