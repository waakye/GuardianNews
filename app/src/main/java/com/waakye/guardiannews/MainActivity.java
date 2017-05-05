package com.waakye.guardiannews;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL for article data from the Guardian dataset */
    private static final String GUARDIAN_REQUEST_URL
        = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";

    /** Adapter for the list of news articles */
    private NewsArticleAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsArticlesListView = (ListView) findViewById(R.id.news_listview);

        // Create a new adapter that takes an empty list of news articles as input
        mAdapter = new NewsArticleAdapter(this, new ArrayList<NewsArticle>());

        // Set the adapter on the {@link ListView} so that the list can be populated in the
        // user interface
        newsArticlesListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected article
        newsArticlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current article that was clicked on
                NewsArticle currentNewsArticle = mAdapter.getItem(position);


                // Convert the String URL into a URI object (to pass into the Internet constructor)
                Uri newsArticleUri = Uri.parse(currentNewsArticle.getUrl());

                // Create a new intent to view the news article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsArticleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Start the AsyncTask to fetch the news article data
        NewsArticleAsyncTask task = new NewsArticleAsyncTask();
        task.execute(GUARDIAN_REQUEST_URL);
    }

    private class NewsArticleAsyncTask extends AsyncTask<String, Void, List<NewsArticle>> {

        /**
         * This method runs on the background thread and performs the network request.  We should
         * not update the UI from the background thread so we return a list of {@link NewsArticle}s
         * as a result
         */
        @Override
        protected List<NewsArticle> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<NewsArticle> results = QueryUtils.fetchNewsArticleData(urls[0]);
            return results;
        }

        /**
         * This method runs on the main UI thread after the background work has been completed.
         * This method receives input from the doInBackground() method.  First, we clear out the
         * adapter, to eliminate the data from a previous query to the Guardian API.  Then we
         * update the adapter with the new list of articles, which will trigger the ListView to
         * re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<NewsArticle> data) {
            // Clear the adapter of previous news articles
            mAdapter.clear();

            // If there is a valid list of {@link NewsArticle}s, then add them to the adapter's
            // data set.  This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
