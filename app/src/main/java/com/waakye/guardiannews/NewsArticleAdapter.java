package com.waakye.guardiannews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lesterlie on 5/5/17.
 */

public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {

    /**
     * Constructs a new {@link NewsArticleAdapter}
     *
     * @param context of the app
     * @param newsArticles is the list of newsArticles, which is the data source of the adapter
     */
    public NewsArticleAdapter(Context context, List<NewsArticle> newsArticles) {
        super(context, 0, newsArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse
        // otherwise, if convertView is null, then inflate a new list item layout
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_article_list_item, parent, false);
        }

        // Find the news article at the given position in the list of news articles
        NewsArticle currentNewsArticle = getItem(position);

        // Find the TextView with the view ID article_title
        TextView articleTitleTextView = (TextView)listItemView.findViewById(R.id.article_title);
        // Display the title of the current article in that TextView
        articleTitleTextView.setText(currentNewsArticle.getWebTitle());

        // Find the TextView the with view ID article_section
        TextView articleSectionTextView = (TextView)listItemView.findViewById(R.id.article_section);
        // Display the section of the current article in that TextView
        articleSectionTextView.setText(currentNewsArticle.getSectionName());

        // Find the TextView with the view ID publication_date
        TextView publicationDateTextView = (TextView)listItemView.findViewById(R.id.pubication_date);
        // Display the date of the current article in that TextView
        publicationDateTextView.setText(currentNewsArticle.getWebPublicationDate());

        // Return the list item view that now shows the appropriate data
        return listItemView;
    }
}
