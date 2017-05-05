package com.waakye.guardiannews;

/**
 * Created by lesterlie on 5/5/17.
 */

public class NewsArticle {

    /** Title of the news article */
    private String webTitle;

    /** Name of the section the article is located */
    private String sectionName;

    /** Constructor a new NewsArticle object
     *
     * @param vWebtitle is the title of the news article
     * @param vSectionName is the name of the section the article can be found in
     */
    public NewsArticle(String vWebtitle, String vSectionName) {
        webTitle = vWebtitle;
        sectionName = vSectionName;
    }

    /**
     * Returns the title of the article
     */
    public String getWebTitle() {
        return webTitle;
    }

    /**
     * Returns the section the article can be found in
     */
    public String getSectionName() {
        return sectionName;
    }

}
