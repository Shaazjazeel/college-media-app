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

public class custom_view_fine extends BaseAdapter {
    String[] fine_id,f_lib,f_image,f_name,f_author,f_bdate,f_ddate,f_rdate,f_days,f_status,f_amount;
    private Context context;

    public custom_view_fine(Context applicationContext, String[] fine_id, String[] f_lib, String[] f_image, String[] f_name, String[] f_author, String[] f_bdate, String[] f_ddate, String[] f_rdate, String[] f_days, String[] f_status, String[] f_amount) {
        this.context=applicationContext;
        this.fine_id=fine_id;
        this.f_lib=f_lib;
        this.f_image=f_image;
        this.f_name=f_name;
        this.f_author=f_author;
        this.f_bdate=f_bdate;
        this.f_ddate=f_ddate;
        this.f_rdate=f_rdate;
        this.f_days=f_days;
        this.f_status=f_status;
        this.f_amount=f_amount;


    }

    @Override
    public int getCount() {
        return f_name.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_fine, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.lib_fine);
        TextView tv2 = (TextView) gridView.findViewById(R.id.f_name);
        TextView tv3 = (TextView) gridView.findViewById(R.id.f_author);
        TextView tv4 = (TextView) gridView.findViewById(R.id.fb_date);
        TextView tv5 = (TextView) gridView.findViewById(R.id.fd_date);

        TextView tv6 = (TextView) gridView.findViewById(R.id.fr_date);
        TextView tv7 = (TextView) gridView.findViewById(R.id.f_days);
        TextView tv8 = (TextView) gridView.findViewById(R.id.f_amount);
        TextView tv9 = (TextView) gridView.findViewById(R.id.ff_status);
        ImageView im=(ImageView) gridView.findViewById(R.id.fine_img);

        tv1.setText(f_lib[i]);
        tv2.setText(f_name[i]);
        tv3.setText(f_author[i]);
        tv4.setText(f_bdate[i]);
        tv5.setText(f_ddate[i]);
        tv6.setText(f_rdate[i]);
        tv7.setText(f_days[i]);
        tv8.setText(f_amount[i]);
        tv9.setText(f_status[i]);

//        rv5.setRating(Float.parseFloat(rating[i]));
//        rv5.setIsIndicator(true);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+f_image[i];

        Picasso.with(context).load(url). transform(new CircleTransform()). into(im);

        return gridView;
    }
}