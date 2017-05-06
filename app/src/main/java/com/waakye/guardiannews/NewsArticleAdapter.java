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

    private static final String DATE_SEPARATOR = "T";

    private static final String HYPHEN_SEPARATOR = "-";

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

        // Get the original web publication date string from the NewsArticle object
        String originalDate = currentNewsArticle.getWebPublicationDate();

        // If the original location string contains "T" then store the date separately from the time
        String primaryDate = null;
        String secondaryDate = null;
        String year = null;
        String month = null;
        String day = null;
        int integerMonth = 0;

        // Check whether the originalDate string contains the "T" text
        if (originalDate.contains(DATE_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings) based on the "T" text.
            // We expect an array of 2 Strings, where the first String will be a date and the
            // second String will be a time
            String[] parts = originalDate.split(DATE_SEPARATOR);
            // Time should be
            secondaryDate = parts[0];

            String[] dateParts = secondaryDate.split(HYPHEN_SEPARATOR);
            year = dateParts[0];
            month = dateParts[1];
            day = dateParts[2];
            integerMonth = Integer.parseInt(month);

            switch (integerMonth) {
                case 1:
                    month = "January";
                    break;
                case 2:
                    month = "February";
                    break;
                case 3:
                    month = "March";
                    break;
                case 4:
                    month = "April";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "June";
                    break;
                case 7:
                    month = "July";
                    break;
                case 8:
                    month = "August";
                    break;
                case 9:
                    month = "September";
                    break;
                case 10:
                    month = "October";
                    break;
                case 11:
                    month = "November";
                    break;
                case 12:
                    month = "December";
                    break;
            }
            secondaryDate = month + " " + day + ", " + year;
        } else {
            // Otherwise
            secondaryDate = originalDate;
        }


        // Find the TextView with the view ID publication_date
        TextView publicationDateTextView = (TextView)listItemView.findViewById(R.id.publication_date);
        // Display the date of the current article in that TextView
        publicationDateTextView.setText(secondaryDate);

        // Return the list item view that now shows the appropriate data
        return listItemView;
    }
}
