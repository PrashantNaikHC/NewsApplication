package com.example.prashant.newsapplication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NewsUtility {
    public static final String LOG_TAG = NewsUtility.class.getSimpleName();

    public NewsUtility() {
    }

    public static List<News> fetchNews(String requestUrl){
        URL url = createURL(requestUrl);
        String JsonResponse = null;
        try{
            JsonResponse=makeHTTPrequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"IO  Exception occured",e);
        }
        List<News> listOfNews = extractFeatureFromJson(JsonResponse);
        return listOfNews;
    }

    private static List<News> extractFeatureFromJson(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        List<News> NewsArrayList = new ArrayList<News>();

        try {
            JSONObject baseJsonObject = new JSONObject(jsonResponse);
            JSONObject jsonObjectResponse = baseJsonObject.getJSONObject("response");
            JSONArray jsonArray = jsonObjectResponse.getJSONArray("results");

            for (int i=0; i< jsonArray.length();i++){
                 JSONObject currentJsonObject = jsonArray.getJSONObject(i);
                 String Headlines = currentJsonObject.getString("webTitle");
                 String newsUrl = currentJsonObject.getString("webUrl");
                 String catogory = currentJsonObject.getString("sectionName");

                 News news = new News(newsUrl,Headlines,catogory);
                 NewsArrayList.add(news);
            }

        }catch (JSONException e){
            Log.e(LOG_TAG,"Json exception occured",e);
        }
            return NewsArrayList;

    }

    private static String makeHTTPrequest(URL url) throws IOException {
        String JsonResponse = "";
        if(url==null){
            return JsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                JsonResponse = readFromStream(inputStream);

            }

            else {
                Log.e(LOG_TAG,"Response code is" + urlConnection.getResponseCode());
            }

        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving the response",e);
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return JsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }

        }return output.toString();
    }

    private static URL createURL(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Malformed URL exception",e);
        }return url;

    }
}
