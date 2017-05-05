package com.waakye.guardiannews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        NewsArticleAdapter adapter = new NewsArticleAdapter(this, newsArticles);

        // Set the adapter on the {@link ListView} so that the list can be populated in the
        // user interface
        newsArticlesListView.setAdapter(adapter);
    }
}
