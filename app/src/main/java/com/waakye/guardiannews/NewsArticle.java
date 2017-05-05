package com.waakye.guardiannews;

/**
 * Created by lesterlie on 5/5/17.
 */

public class NewsArticle {

    /** Title of the news article */
    private String webTitle;

    /** Name of the section the article is located */
    private String sectionName;

    /** Website URL of news article */
    private String url;

    /** Web publication date of the article */
    private String webPublicationDate;

    /** Constructor a new NewsArticle object
     *
     * @param vWebtitle is the title of the news article
     * @param vSectionName is the name of the section the article can be found in
     */
    public NewsArticle(String vWebtitle, String vSectionName, String vUrl,
                       String vWebPublicationDate) {
        webTitle = vWebtitle;
        sectionName = vSectionName;
        url = vUrl;
        webPublicationDate = vWebPublicationDate;
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

    /**
     * Returns the url related to the article
     */
    public String getUrl() {
        return url;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

}
