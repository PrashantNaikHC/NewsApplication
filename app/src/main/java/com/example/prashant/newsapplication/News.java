package com.example.prashant.newsapplication;

public class News {

    private String mUrl;
    private String MHeadlines;
    private String MCatogorrylines;

    public News(String mUrl, String MHeadlines, String MCatogorrylines) {
        this.mUrl = mUrl;
        this.MCatogorrylines = MCatogorrylines;
        this.MHeadlines = MHeadlines;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getMHeadlines() {
        return MHeadlines;
    }

    public String getMCatogorrylines() {
        return MCatogorrylines;
    }
}
