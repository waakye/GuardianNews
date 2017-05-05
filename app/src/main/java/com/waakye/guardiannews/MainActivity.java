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

        // Create a fake list of news article results
        ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();
        newsArticles.add(new NewsArticle("10 Ways to Hike a Mountain", "Sports"));
        newsArticles.add(new NewsArticle("10 Ways to Swim the English Channel", "Sports"));
        newsArticles.add(new NewsArticle("10 Ways to Reduce Your Marathon Time", "Sports"));
        newsArticles.add(new NewsArticle("10 Ways to Play Golf", "Sports"));

        // Find a reference to the {@link ListView} in the layout
        ListView newsArticlesListView = (ListView) findViewById(R.id.news_listview);

        // Create a new adapter that takes the list of news articles as input
        NewsArticleAdapter adapter = new NewsArticleAdapter(this, newsArticles);

        // Set the adapter on the {@link ListView} so that the list can be populated in the
        // user interface
        newsArticlesListView.setAdapter(adapter);
    }
}
