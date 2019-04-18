package com.example.prashant.newsapplication;

import android.content.Context;
import android.content.Loader;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private final String LOG_TAG = NewsLoader.class.getSimpleName();
    private String mURL;


    public NewsLoader(Context context, String newsUrl) {
        super(context);
        mURL=newsUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if(mURL==null){
            return null;
        }
        List<News> newsResults = NewsUtility.fetchNews(mURL);
        return newsResults;
    }
}
