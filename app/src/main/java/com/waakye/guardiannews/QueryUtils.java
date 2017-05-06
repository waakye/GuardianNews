package com.waakye.guardiannews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lesterlie on 5/5/17.
 * Helper methods related to requesting and receiving news search data from Guardian API
 */


public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     */
    private QueryUtils() {}

    /**
     * Query the Guardian API and return a list of {@link NewsArticle} objects
     */
    public static List<NewsArticle> fetchNewsArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link NewsArticle} objects
        List<NewsArticle> newsArticles = extractResultFromJson(jsonResponse);

        // Return the list of {@link NewsArticle}s
        return newsArticles;
    }

    /**
     * Returns new URL object from the given string URL
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ",e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and
            // parse the response code.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news article JSON results.", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the whole JSON response from
     * the server
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link NewsArticle} objects that have been built up from parsing the given
     * JSON response
     */
    private static List<NewsArticle> extractResultFromJson(String newsArticleJson) {
        // If the JSON String is empty or null, then return early
        if (TextUtils.isEmpty(newsArticleJson)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news articles to
        List<NewsArticle> newsArticles = new ArrayList<>();

        // Try to parse the JSON response string.  If there is a problem with the way the JSON is
        // formatted, a JSONException exception object will be thrown.
        try {

            JSONObject root = new JSONObject(newsArticleJson);
            JSONObject responseObject = root.getJSONObject("response");
            JSONArray results = responseObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                // Get the article at position i
                JSONObject currentArticle = results.getJSONObject(i);

                // Get the article title
                String title = currentArticle.getString("webTitle");

                // Get the section appropriate for the article
                String section = currentArticle.getString("sectionName");

                // Get the webUrl for the article
                String url = currentArticle.getString("webUrl");

                // Get the publication date of the article
                String publicationDate = currentArticle.getString("webPublicationDate");

                // Create a NewsArticle object
                NewsArticle article = new NewsArticle(title, section, url, publicationDate);

                // Add NewsArticle object to list of news articles
                newsArticles.add(article);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash.  Print a log message with the
            // message from the exception
            Log.e("QueryUtils", "Problem parsing the news article JSON results", e);
        }

        return newsArticles;
    }
}


