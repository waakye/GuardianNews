package com.waakye.guardiannews;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsArticle>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL for article data from the Guardian dataset */
    private static final String GUARDIAN_REQUEST_URL
        = "http://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";

    /** URL search prefix from the Guardian website */
    private static final String GUARDIAN_PREFIX
            = "http://content.guardianapis.com/search?q=";

    private static final String API_TEST_KEY = "&api-key=test";

    /**
     * Constant value for the news article loader ID
     */
    private static final int NEWS_ARTICLE_LOADER_ID = 1;

    public String search_terms = "";

    private String concatenated_search_terms = "";

    /** Adapter for the list of news articles */
    private NewsArticleAdapter mAdapter;

    private String urlQueryString(String search_terms) {
        StringBuilder sb = new StringBuilder(GUARDIAN_PREFIX);
        concatenated_search_terms = search_terms.trim().replace(" ", "+");
        sb.append(concatenated_search_terms);
        sb.append(API_TEST_KEY);
        String builtString = sb.toString();
        return builtString;
    }

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

        Button searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                // Get input from the EditText
                EditText searchTerms = (EditText)findViewById(R.id.edit_text_search_terms);
                search_terms = searchTerms.getText().toString();

                // Tests whether anything was entered
                if (search_terms.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nothing entered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something entered", Toast.LENGTH_SHORT).show();
                }
            }
        });


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

        // Get a reference to the LoaderManager, in order to interact with loaders
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader
        loaderManager.initLoader(NEWS_ARTICLE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsArticleLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> data) {
        // Clear the adapter of previous news article data
        mAdapter.clear();

        // If there is a valid list of {@link NewsArticle}s, then add them to the adapter's data
        // set. This will trigger the ListView to update
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        // Loader reset, so we can clear out our existing data
        mAdapter.clear();
    }

}
