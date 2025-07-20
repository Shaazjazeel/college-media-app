package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_friendrequest_status extends BaseAdapter {

    String[] req_id,name,image,department,status;
    private Context context;
    public custom_view_friendrequest_status(Context applicationContext, String[] req_id, String[] name, String[] image, String[] department, String[] status) {
        this.context=applicationContext;
        this.req_id=req_id;
        this.name=name;
        this.image=image;
//        this.email=email;
        this.department=department;
        this.status=status;

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
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_friendrequest_status,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView11);
//        TextView tv2=(TextView)gridView.findViewById(R.id.textView12);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView13);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView14);

        ImageView im=(ImageView) gridView.findViewById(R.id.imageView3);
//        Button b1=(Button)gridView.findViewById(R.id.button16);



        tv1.setTextColor(Color.BLUE);


        tv1.setText(name[i]);
//        tv2.setText(email[i]);
        tv3.setText(department[i]);
        tv4.setText(status[i]);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+image[i];

        Picasso.with(context).load(url). transform(new CircleTransform()). into(im);

        return gridView;
    }
}