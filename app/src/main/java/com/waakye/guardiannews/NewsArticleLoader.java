package com.waakye.guardiannews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by lesterlie on 5/6/17.
 */

public class NewsArticleLoader extends AsyncTaskLoader<List<NewsArticle>> {

    /** Tag for log responses */
    private static final String LOG_TAG = NewsArticleLoader.class.getSimpleName();

    /** Query URL */
    private String url;

    /**
     * Constructs a new {@link NewsArticleLoader}
     * @param vContext of the activity
     * @param vUrl to load the data
     */
    public NewsArticleLoader(Context vContext, String vUrl) {
        super(vContext);
        url = vUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsArticle> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news articles
        List<NewsArticle> newsArticles = QueryUtils.fetchNewsArticleData(url);
        return newsArticles;
    }
}
