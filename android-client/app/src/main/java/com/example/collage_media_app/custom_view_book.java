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

public class custom_view_book extends BaseAdapter {

    private Context context;
    SharedPreferences sh;
    String url;
    String[] book_id,bname,bimage,bauthor,bprice,bpages;
    public custom_view_book(Context applicationContext, String[] book_id, String[] bname, String[] bimage, String[] bauthor, String[] bprice, String[] bpages) {
        this.context=applicationContext;
        this.book_id=book_id;
        this.bname=bname;
        this.bimage=bimage;
        this.bauthor=bauthor;
        this.bprice=bprice;
        this.bpages=bpages;



    }

    @Override
    public int getCount() {
        return bname.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_book, null);

        } else {
            gridView = (View) view;

        }

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView3);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView4);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView20);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView22);

        ImageView im=(ImageView) gridView.findViewById(R.id.book_img);



        tv1.setText(bname[i]);
        tv2.setText(bauthor[i]);
        tv3.setText(bpages[i]);
        tv4.setText(bprice[i]);

//        rv5.setRating(Float.parseFloat(rating[i]));
//        rv5.setIsIndicator(true);

//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("imageurl","");
//        String url=ip+bimage[i];
//        Toast.makeText(context, ""+url, Toast.LENGTH_SHORT).show();
//        Log.d("image",url);
//        Picasso.with(context).load(url).transform(new CircleTransform()). into(im);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+bimage[i];

        Picasso.with(context).load(url). transform(new CircleTransform()). into(im);

        return gridView;
    }
}