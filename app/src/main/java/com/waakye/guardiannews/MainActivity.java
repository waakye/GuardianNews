package com.waakye.guardiannews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<NewsArticle> newsArticles = QueryUtils.extractNewsArticles();

        // Find a reference to the {@link ListView} in the layout
        ListView newsArticlesListView = (ListView) findViewById(R.id.news_listview);

        // Create a new adapter that takes the list of news articles as input
        final NewsArticleAdapter adapter = new NewsArticleAdapter(this, newsArticles);

        // Set the adapter on the {@link ListView} so that the list can be populated in the
        // user interface
        newsArticlesListView.setAdapter(adapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected article
        newsArticlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current article that was clicked on
                NewsArticle currentNewsArticle = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Internet constructor)
                Uri newsArticleUri = Uri.parse(currentNewsArticle.getUrl());

                // Create a new intent to view the news article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsArticleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }
}
