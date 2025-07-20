package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_comment_otherpost extends BaseAdapter {
    String[]cmdid,date,image,name,comment;
    private Context context;

    public custom_view_comment_otherpost(Context applicationContext, String[] cmdid, String[] date, String[] image, String[] name, String[] comment) {
        this.context=applicationContext;
        this.cmdid=cmdid;
        this.date=date;   this.image=image;
        this.name=name;
        this.comment=comment;

    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_comment_otherpost, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView12);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView17);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView18);

        ImageView im = (ImageView) gridView.findViewById(R.id.imageView9);


        tv1.setText(date[i]);
        tv2.setText(name[i]);
        tv3.setText(comment[i]);



//        rv5.setRating(Float.parseFloat(rating[i]));
//        rv5.setIsIndicator(true);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+image[i];

        Picasso.with(context).load(url). transform(new CircleTransform()). into(im);

        return gridView;
    }
}