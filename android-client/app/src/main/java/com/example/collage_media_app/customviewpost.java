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
import com.example.collage_media_app.R;
import com.example.collage_media_app.home;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class customviewpost extends BaseAdapter {
    String[]id,im,title,description,like;
    Button delete,likecomment,edit;
    private  Context context;

    public customviewpost(Context applicationContext, String[] id, String[] im, String[] title, String[] description, String[] like) {
        this.context=applicationContext;
        this.id=id;
        this.im=im;
        this.title= title;
        this.description= description;
        this.like=like;


    }

    @Override
    public int getCount() {
        return title.length;
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
            gridView=inflator.inflate(R.layout.activity_customviewpost,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView75);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView76);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView93);





        Button  delete=(Button)gridView.findViewById(R.id.button9);
        Button  edit=(Button)gridView.findViewById(R.id.button15);
        Button  likecomment=(Button)gridView.findViewById(R.id.button10);

        tv1.setText(title[i]);
        tv2.setText(description[i]);
        tv3.setText(like[i]);



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                String url=sh.getString("url","")+"/deletepost";

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

                                        Intent i =new Intent(context.getApplicationContext(), home.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);
                                        Toast.makeText(context.getApplicationContext(), "Deleted successfully", Toast.LENGTH_LONG).show();


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
        edit.setTag(i);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a=(int)view.getTag();
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("pid",id[a]);
                ed.commit();
                Intent i=new Intent(context.getApplicationContext(),edit_post.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        likecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("psid",id[i]);
                ed.commit();
                Intent j =new Intent(context.getApplicationContext(),view_mypost_comment.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);

            }
        });

        ImageView im1=(ImageView) gridView.findViewById(R.id.imageView14);

//        tv5.setText([i]);




        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url=sh.getString("url","")+im[i];

        Picasso.with(context).load(url). into(im1);

        return gridView;
    }
}