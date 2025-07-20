package com.example.collage_media_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class customviewlikeandcomment extends BaseAdapter {
    String[] id,name,comment,date;
    String url;
    SharedPreferences sh;
private Context context;
    public customviewlikeandcomment(Context applicationContext, String[] id, String[] name, String[] comment, String[] date) {
        this.context=applicationContext;
        this.id=id;
        this.date=date;
        this.name=name;
        this.comment=comment;

    }



    @Override
    public int getCount() {
        return id.length;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_customviewlikeandcomment,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView67);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView91);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView92);

//        TextView tv5=(TextView)gridView.findViewById(R.id.);

//        ImageView im=(ImageView) gridView.findViewById(R.id.imageView15);


        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(comment[i]);
        tv3.setText(date[i]);
//        tv5.setText([i]);



//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//
//        String url="http://" + ip + ":5000/static/game/"+gamecode[i]+".jpg";
//
//
//        Picasso.with(context).load(url).transform(new CircleTransform()). into(im);

        return gridView;
    }
}