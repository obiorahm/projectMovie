package com.example.mgo983.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by mgo983 on 12/4/16.
 */

public class ImageGridAdapter extends ArrayAdapter{

    private Context context;

    private LayoutInflater inflater;

    private String[] imageUrls;

    public ImageGridAdapter(Context context, String[] imageUrls){
        super(context, R.layout.film_grid_item,imageUrls);

        this.context = context;

        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = inflater.inflate(R.layout.film_grid_item,parent,false);

        }
        Picasso
                .with(context)
                .load(imageUrls[position])
                .fit()
                .into((ImageView) convertView);

        return convertView;
    }
}
