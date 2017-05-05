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

    /** Sample JSON response for a Guardian query */
    private static final String SAMPLE_JSON_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":23488,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":2349,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"politics/2017/apr/18/theresa-may-rules-out-participating-in-tv-debates-before-election\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2017-04-18T17:20:59Z\",\"webTitle\":\"Theresa May rules out participating in TV debates before election\",\"webUrl\":\"https://www.theguardian.com/politics/2017/apr/18/theresa-may-rules-out-participating-in-tv-debates-before-election\",\"apiUrl\":\"https://content.guardianapis.com/politics/2017/apr/18/theresa-may-rules-out-participating-in-tv-debates-before-election\",\"isHosted\":false},{\"id\":\"politics/live/2017/mar/21/death-martin-mcguinness-reaction-politics-live\",\"type\":\"liveblog\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2017-03-21T17:29:43Z\",\"webTitle\":\"Scottish parliament debates call for second independence referendum - Politics live\",\"webUrl\":\"https://www.theguardian.com/politics/live/2017/mar/21/death-martin-mcguinness-reaction-politics-live\",\"apiUrl\":\"https://content.guardianapis.com/politics/live/2017/mar/21/death-martin-mcguinness-reaction-politics-live\",\"isHosted\":false},{\"id\":\"politics/2017/apr/19/bbc-and-itv-plan-leaders-debates-despite-mays-refusal-to-take-part\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2017-04-19T14:01:59Z\",\"webTitle\":\"May 'open to other options' after ruling out head-to-head television debates\",\"webUrl\":\"https://www.theguardian.com/politics/2017/apr/19/bbc-and-itv-plan-leaders-debates-despite-mays-refusal-to-take-part\",\"apiUrl\":\"https://content.guardianapis.com/politics/2017/apr/19/bbc-and-itv-plan-leaders-debates-despite-mays-refusal-to-take-part\",\"isHosted\":false},{\"id\":\"commentisfree/2017/apr/19/theresa-may-chickening-out-tv-debates-shameful-caroline-lucas\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2017-04-19T12:54:55Z\",\"webTitle\":\"Chickening out of TV debates is shameful. Why is May avoiding us? | Caroline Lucas\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2017/apr/19/theresa-may-chickening-out-tv-debates-shameful-caroline-lucas\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2017/apr/19/theresa-may-chickening-out-tv-debates-shameful-caroline-lucas\",\"isHosted\":false},{\"id\":\"us-news/2016/sep/26/presidential-debates-nixon-kennedy-1960\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-09-26T15:57:34Z\",\"webTitle\":\"The Nixon-Kennedy presidential debates: from the archive, 1960\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/sep/26/presidential-debates-nixon-kennedy-1960\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/sep/26/presidential-debates-nixon-kennedy-1960\",\"isHosted\":false},{\"id\":\"politics/2017/apr/19/theresa-may-accused-at-pmqs-of-running-scared-from-tv-debates\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2017-04-19T12:10:28Z\",\"webTitle\":\"Theresa May accused at PMQs of running scared from TV debates\",\"webUrl\":\"https://www.theguardian.com/politics/2017/apr/19/theresa-may-accused-at-pmqs-of-running-scared-from-tv-debates\",\"apiUrl\":\"https://content.guardianapis.com/politics/2017/apr/19/theresa-may-accused-at-pmqs-of-running-scared-from-tv-debates\",\"isHosted\":false},{\"id\":\"politics/live/2017/apr/26/general-election-2017-may-corbyn-final-pmqs-politics-live\",\"type\":\"liveblog\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2017-04-26T16:42:46Z\",\"webTitle\":\"General election 2017: Theresa May and Jeremy Corbyn square off in final PMQs before election – politics live\",\"webUrl\":\"https://www.theguardian.com/politics/live/2017/apr/26/general-election-2017-may-corbyn-final-pmqs-politics-live\",\"apiUrl\":\"https://content.guardianapis.com/politics/live/2017/apr/26/general-election-2017-may-corbyn-final-pmqs-politics-live\",\"isHosted\":false},{\"id\":\"culture/shortcuts/2017/feb/06/the-next-doctor-who-a-black-bond-the-pop-culture-debates-that-never-end\",\"type\":\"article\",\"sectionId\":\"culture\",\"sectionName\":\"Culture\",\"webPublicationDate\":\"2017-02-06T07:00:20Z\",\"webTitle\":\"The next Doctor Who, a black Bond … the pop culture debates that never end\",\"webUrl\":\"https://www.theguardian.com/culture/shortcuts/2017/feb/06/the-next-doctor-who-a-black-bond-the-pop-culture-debates-that-never-end\",\"apiUrl\":\"https://content.guardianapis.com/culture/shortcuts/2017/feb/06/the-next-doctor-who-a-black-bond-the-pop-culture-debates-that-never-end\",\"isHosted\":false},{\"id\":\"commentisfree/2016/sep/23/presidential-debates-real-time-reactions-notifications-mobile\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2016-09-23T21:43:23Z\",\"webTitle\":\"Get real-time reactions during the presidential debates | Guardian Mobile Innovation Lab\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2016/sep/23/presidential-debates-real-time-reactions-notifications-mobile\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2016/sep/23/presidential-debates-real-time-reactions-notifications-mobile\",\"isHosted\":false},{\"id\":\"us-news/2016/sep/25/us-presidential-debates-famous-moments\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-09-25T10:00:31Z\",\"webTitle\":\"Make or break: the defining moments of presidential debates\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/sep/25/us-presidential-debates-famous-moments\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/sep/25/us-presidential-debates-famous-moments\",\"isHosted\":false}]}}";

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

            JSONObject root = new JSONObject(SAMPLE_JSON_RESPONSE);
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
//
//    /**
//     * Return a list of {@link NewsArticle} objects that have been built up from parsing a JSON response
//     */
//    public static ArrayList<NewsArticle> extractNewsArticles() {
//
//        // Create an empty ArrayList that we can start adding news articles to
//        ArrayList<NewsArticle> newsArticles = new ArrayList<>();
//
//        // Try to parse the SAMPLE_JSON_RESPONSE. If there is problem with the way the JSON is
//        // formatted, a JSONException exception object will be thrown.
//        // Catch the exception so the app doesn't crash and print the error message to the logs.
//        try {
//
//            JSONObject root = new JSONObject(SAMPLE_JSON_RESPONSE);
//            JSONObject responseObject = root.getJSONObject("response");
//            JSONArray results = responseObject.getJSONArray("results");
//
//            for (int i = 0; i < results.length(); i++) {
//
//                // Get the article at position i
//                JSONObject currentArticle = results.getJSONObject(i);
//
//                // Get the article title
//                String title = currentArticle.getString("webTitle");
//
//                // Get the section appropriate for the article
//                String section = currentArticle.getString("sectionName");
//
//                // Get the webUrl for the article
//                String url = currentArticle.getString("webUrl");
//
//                // Get the publication date of the article
//                String publicationDate = currentArticle.getString("webPublicationDate");
//
//                // Create a NewsArticle object
//                NewsArticle article = new NewsArticle(title, section, url, publicationDate);
//
//                // Add NewsArticle object to list of news articles
//                newsArticles.add(article);
//
//            }
//
//        } catch (JSONException e) {
//            // If an error is thrown when executing any of the above statements in the "try" block,
//            // catch the exception here, so the app doesn't crash.  Print a log message with the
//            // message from the exception
//            Log.e("QueryUtils", "Problem parsing the news article JSON results", e);
//        }
//
//        return newsArticles;
//    }

