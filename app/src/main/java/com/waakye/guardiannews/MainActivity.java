package com.waakye.guardiannews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a fake list of news article results
        ArrayList<String> newsArticles = new ArrayList<String>();
        newsArticles.add("10 Ways to Hike a Mountain");
        newsArticles.add("10 Ways to Swim the English Channel");
        newsArticles.add("10 Ways to Reduce Your Marathon Time");
        newsArticles.add("10 Ways to Play Golf");

        // Find a reference to the {@link ListView} in the layout
        ListView newsArticlesListView = (ListView) findViewById(R.id.news_listview);

        // Create a new {@link ArrayAdapter} of news articles
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, newsArticles);

        // Set the adapter on the {@link ListView} so that the list can be populated in the
        // user interface
        newsArticlesListView.setAdapter(adapter);
    }
}
