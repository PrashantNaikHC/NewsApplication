package com.example.prashant.newsapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class customNewsAdapter extends ArrayAdapter<News>{



    public customNewsAdapter(@NonNull Context context, ArrayList<News> newslist) {
        super(context, 0,newslist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View listItemView = convertView;

        if(listItemView==null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_main_detailed,parent,false);
        }

        News CurrentNews = (News) getItem(position);

        Typeface summer = Typeface.createFromAsset(getContext().getAssets(),"fonts/Summer.ttf");


        TextView news = (TextView) listItemView.findViewById(R.id.newsText);
        news.setTypeface(summer);
        news.setText(CurrentNews.getMHeadlines());

        TextView subject = (TextView) listItemView.findViewById(R.id.catogory);
        subject.setText(CurrentNews.getMCatogorrylines());

        return listItemView;
    }
}
