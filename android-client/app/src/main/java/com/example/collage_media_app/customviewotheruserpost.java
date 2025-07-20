package com.example.collage_media_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class customviewotheruserpost extends BaseAdapter {
    String[] id,name,title,im,description,like,comment;

    String url;
    SharedPreferences sh;
    private Context context;

    public customviewotheruserpost(Context applicationContext, String[] id, String[] name, String[] im, String[] title, String[] description,String[] like,String[] comment) {
        this.context=applicationContext;
        this.id=id;
        this.name=name;
        this.im=im;
        this.title=title;
        this.description=description;
        this.like=like;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_customviewotheruserpost,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView90);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView89);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView78);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView6);
        TextView tv6=(TextView)gridView.findViewById(R.id.textView7);

//        Button comment=(Button)gridView.findViewById(R.id.button18);
        ImageView lke=(ImageView)gridView.findViewById(R.id.imageView6);
        ImageView cont=(ImageView)gridView.findViewById(R.id.imageView7);
        cont.setTag(i);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                int a=(int)view.getTag();
                ed.putString("pid",id[a]);
                ed.commit();
                Intent i =new Intent(context.getApplicationContext(),view_comment_otherpost.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });




        lke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                String url=sh.getString("url","")+"/likepost";

                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                        Intent i =new Intent(context.getApplicationContext(),viewotheruserpost.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);
                                        Toast.makeText(context.getApplicationContext(), "like successfully", Toast.LENGTH_LONG).show();


                                    }
                                    else {
                                        Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                    }

                                }    catch (Exception e) {
                                    Toast.makeText(context.getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("pid",id[i]);
                        params.put("lid",sh.getString("lid",""));


                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS=100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);



            }
        });

        ImageView im1=(ImageView) gridView.findViewById(R.id.imageView18);

        tv1.setTextColor(Color.BLACK);

        tv1.setText(name[i]);
        tv2.setText(title[i]);
        tv3.setText(description[i]);
        tv5.setText(like[i]);
        tv6.setText(comment[i]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+im[i];

        Picasso.with(context).load(url). into(im1);

        return gridView;
    }
}