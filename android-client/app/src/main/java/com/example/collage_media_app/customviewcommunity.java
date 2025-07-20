package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class customviewcommunity extends BaseAdapter {

    String[]id,comname,url;
    SharedPreferences sh;
    private Context context;
    public customviewcommunity(Context applicationContext, String[] id, String[] comname) {
        this.context=applicationContext;
        this.id=id;
        this.comname=comname;

    }

    @Override
    public int getCount() {
        return comname.length;
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
            gridView=inflator.inflate(R.layout.activity_customviewcommunity,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView74);
        Button delete=(Button) gridView.findViewById(R.id.button13);
        Button edit=(Button) gridView.findViewById(R.id.button14);
        Button b2=(Button) gridView.findViewById(R.id.button7);
        Button b3=(Button) gridView.findViewById(R.id.button21);

        b3.setTag(i);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int a=(int)view.getTag();
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("com_id",id[a]);
                ed.commit();
                Intent i=new Intent(context.getApplicationContext(),chat_community.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

//        b1.setTag(i);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int a=(int)view.getTag();
//                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//                SharedPreferences.Editor ed=sh.edit();
//                ed.putString("com_id",id[a]);
//                ed.commit();
//                Intent i=new Intent(context.getApplicationContext(),view_post_on_community.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            }
//        });
        b2.setTag(i);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a=(int)view.getTag();
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("com_id",id[a]);
                ed.commit();
                Intent i=new Intent(context.getApplicationContext(),view_members_in_community.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                String url=sh.getString( "url","")+"/and_deletecommunity";
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
                                        Toast.makeText(context.getApplicationContext(), "Deleted successfully", Toast.LENGTH_LONG).show();
                                        Intent i=new Intent(context.getApplicationContext(),home.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);


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

                        params.put("com_id",id[i]);
                        params.put("lid",sh.getString("lid",""));
//                            params.put("pid",sh.getString("pid",""));

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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("com_id",id[i]);
                ed.commit();
                Intent i=new Intent(context.getApplicationContext(),editcommunity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

//        TextView tv5=(TextView)gridView.findViewById(R.id.);

        // ImageView im1=(ImageView) gridView.findViewById(R.id.imageView4);

        tv1.setTextColor(Color.BLACK);



        tv1.setText(comname[i]);

//        tv5.setText([i]);


//            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//            String ip=sh.getString("ip","");
//
//           // String url=sh.getString("url","")+im[i];
//
//
//          //  Picasso.with(context).load(url).transform(new CircleTransform()). into(im1);

        return gridView;

    }

}
