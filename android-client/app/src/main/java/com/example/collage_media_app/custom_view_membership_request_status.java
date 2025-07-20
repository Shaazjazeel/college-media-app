package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class custom_view_membership_request_status extends BaseAdapter {

    String[] me_id,date,lib_name,place,status;
    private Context context;
    public custom_view_membership_request_status(Context applicationContext, String[] me_id, String[] date, String[] lib_name, String[] place, String[] status) {

        this.context=applicationContext;
        this.me_id=me_id;
        this.date=date;
//        this.library=library;
        this.lib_name=lib_name;
        this.place=place;
        this.status=status;
    }

    @Override
    public int getCount() {
        return place.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_membership_request_status, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.re_date);
//        TextView tv2 = (TextView) gridView.findViewById(R.id.req_lib);
        TextView tv3 = (TextView) gridView.findViewById(R.id.re_liinam);
        TextView tv4 = (TextView) gridView.findViewById(R.id.re_place);
        TextView tv5 = (TextView) gridView.findViewById(R.id.re_statys);

        Button b1=(Button)gridView.findViewById(R.id.button_apl);
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a= (int) view.getTag();
                Toast.makeText(context, ""+me_id[a], Toast.LENGTH_SHORT).show();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("me_id", me_id[a]);
                ed.commit();
                Intent i = new Intent(context, view_book.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


        tv1.setText(date[i]);
//        tv2.setText(library[i]);
        tv3.setText(lib_name[i]);
        tv4.setText(place[i]);
        tv5.setText(status[i]);

//        rv5.setRating(Float.parseFloat(rating[i]));
//        rv5.setIsIndicator(true);

//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//        String url="http://" + ip + ":7045"+image[i];
//        Toast.makeText(context.getApplicationContext(), ""+url, Toast.LENGTH_LONG).show();

//        Picasso.with(context).load(url).transform(new CircleTransform()). into(im);

        return gridView;
    }
}