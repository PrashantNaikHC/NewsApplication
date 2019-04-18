package com.example.prashant.newsapplication;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    /*
    1.Declare the website URL
    2.create the custom adapter
     */
    private customNewsAdapter mAdapter;

    TextView emptyView;

    private static final String NEWS_URL="http://content.guardianapis.com/search";

    private static final int NEWS_LOADER_ID = 1;

    //TODO empty view and the progressbar

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);

        LoaderManager loaderManager = getLoaderManager();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isWifi = networkInfo.getType() == connectivityManager.TYPE_WIFI;

        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            if(isWifi){
                Toast.makeText(this, "Fetching news on WiFi", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Fetching news on Mobile data", Toast.LENGTH_SHORT).show();
            }
            loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID,null,this);

        }
        else{
            TextView noNetwork = (TextView) findViewById(R.id.no_connection);
            noNetwork.setVisibility(View.VISIBLE);
        }


        //loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        loaderManager.initLoader(NEWS_LOADER_ID,null,this);


        ListView newsList = (ListView) findViewById(R.id.list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        newsList.setEmptyView(emptyView);



        mAdapter = new customNewsAdapter(this, new ArrayList<News>());
        newsList.setAdapter(mAdapter);



        loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        //TextView test = (TextView) findViewById(R.id.test);
        //test.setText(formattedDate);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News CurrentNews = (News) mAdapter.getItem(position);
                String website = CurrentNews.getmUrl();
                Uri uri  = Uri.parse(website);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });



    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        //this is the funtion to get the current date in string format
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        Uri baseUri = Uri.parse(NEWS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q","debates");
        uriBuilder.appendQueryParameter("from-date",formattedDate);
        uriBuilder.appendQueryParameter("api-key","test");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        emptyView.setText("No News found");
        mAdapter.clear();
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }


}
