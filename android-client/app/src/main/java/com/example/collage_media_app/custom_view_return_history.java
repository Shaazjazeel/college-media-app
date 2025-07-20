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

public class custom_view_return_history extends BaseAdapter {

    private Context context;
    SharedPreferences sh;
    String url;
    String[] rb_id,rb_image,rb_name,rb_author,rb_date,rbdue_date,rb_return_date,rb_status;
    public custom_view_return_history(Context applicationContext, String[] rb_id, String[] rb_image, String[] rb_name, String[] rb_author, String[] rb_date, String[] rbdue_date, String[] rb_return_date, String[] rb_status) {
        this.context=applicationContext;
        this.rb_id=rb_id;
        this.rb_image=rb_image;
        this.rb_name=rb_name;
        this.rb_author=rb_author;
        this.rb_date=rb_date;
        this.rbdue_date=rbdue_date;
        this.rb_return_date=rb_return_date;
        this.rb_status=rb_status;


    }

    @Override
    public int getCount() {
        return rb_author.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_return_history, null);

        } else {
            gridView = (View) view;

        }

        TextView tv1 = (TextView) gridView.findViewById(R.id.f_name);
        TextView tv2 = (TextView) gridView.findViewById(R.id.f_author);
        TextView tv3 = (TextView) gridView.findViewById(R.id.fb_date);
        TextView tv4 = (TextView) gridView.findViewById(R.id.fd_date);
        TextView tv5 = (TextView) gridView.findViewById(R.id.fr_date);
        TextView tv6 = (TextView) gridView.findViewById(R.id.f_days);
        ImageView im=(ImageView) gridView.findViewById(R.id.fine_img);



        tv1.setText(rb_name[i]);
        tv2.setText(rb_author[i]);
        tv3.setText(rb_date[i]);
        tv4.setText(rbdue_date[i]);
        tv5.setText(rb_return_date[i]);
        tv6.setText(rb_status[i]);
//        rv5.setRating(Float.parseFloat(rating[i]));
//        rv5.setIsIndicator(true);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+rb_image[i];

        Picasso.with(context).load(url). transform(new CircleTransform()). into(im);

        return gridView;
    }
}