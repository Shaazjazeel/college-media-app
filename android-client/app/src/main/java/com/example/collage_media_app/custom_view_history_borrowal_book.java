package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class custom_view_history_borrowal_book extends BaseAdapter {

    private Context context;
    SharedPreferences sh;
    String url;
    String[] bb_id,bb_image,bb_name,bb_author,bb_date,bbdue_date,bb_status;
    public custom_view_history_borrowal_book(Context applicationContext, String[] bb_id, String[] bb_image, String[] bb_name, String[] bb_author, String[] bb_date, String[] bbdue_date, String[] bb_status) {
        this.context=applicationContext;
        this.bb_id=bb_id;
        this.bb_image=bb_image;
        this.bb_name=bb_name;
        this.bb_author=bb_author;
        this.bb_date=bb_date;
        this.bbdue_date=bbdue_date;

        this.bb_status=bb_status;

    }

    @Override
    public int getCount() {
        return bb_name.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_history_borrowal_book, null);

        } else {
            gridView = (View) view;

        }

        TextView tv1 = (TextView) gridView.findViewById(R.id.bbname);
        TextView tv2 = (TextView) gridView.findViewById(R.id.bbauthor);
        TextView tv3 = (TextView) gridView.findViewById(R.id.fb_date);
        TextView tv4 = (TextView) gridView.findViewById(R.id.fd_date);
        TextView tv5 = (TextView) gridView.findViewById(R.id.f_days);
//        TextView tv6 = (TextView) gridView.findViewById(R.id.textView22);
        ImageView im=(ImageView) gridView.findViewById(R.id.bor_bimg);



        tv1.setText(bb_name[i]);
        tv2.setText(bb_author[i]);
        tv3.setText(bb_date[i]);
        tv4.setText(bbdue_date[i]);
        tv5.setText(bb_status[i]);
//        rv5.setRating(Float.parseFloat(rating[i]));
//        rv5.setIsIndicator(true);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+bb_image[i];

        Picasso.with(context).load(url). transform(new CircleTransform()). into(im);

        return gridView;
    }
}