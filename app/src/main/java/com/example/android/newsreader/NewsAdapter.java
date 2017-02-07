package com.example.android.newsreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Francislainy on 06/02/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, ArrayList<News> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        News news = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items_layout, parent, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.title_text_view);
        TextView descriptionView = (TextView) convertView.findViewById(R.id.description_text_view);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);

        titleView.setText(news.getTitle());
        descriptionView.setText(news.getDescription());

        imageView.setImageBitmap(news.getImage());

        return convertView;
    }
}

























