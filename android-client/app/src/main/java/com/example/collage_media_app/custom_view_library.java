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

public class custom_view_library extends BaseAdapter {
    private Context context;
    SharedPreferences sh;
    String url;
    String[] lib_id,name,place,email,phone;

    public custom_view_library(Context applicationContext, String[] lib_id, String[] name, String[] place, String[] email, String[] phone) {
        this.context=applicationContext;
        this.lib_id=lib_id;
        this.name=name;
//        this.library=library;
        this.place=place;
        this.email=email;
        this.phone=phone;


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
            gridView = inflator.inflate(R.layout.activity_custom_view_library, null);

        } else {
            gridView = (View) view;

        }

//        TextView tv1 = (TextView) gridView.findViewById(R.id.library_id);
        TextView tv2 = (TextView) gridView.findViewById(R.id.l_name_id);
        TextView tv3 = (TextView) gridView.findViewById(R.id.l_place_id);
        TextView tv4 = (TextView) gridView.findViewById(R.id.l_phn_id);
        TextView tv5 = (TextView) gridView.findViewById(R.id.l_eml_id);
        Button b1=(Button)gridView.findViewById(R.id.button9);
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a= (int) view.getTag();
//                Toast.makeText(context, ""+id[a], Toast.LENGTH_SHORT).show();
                sh= PreferenceManager.getDefaultSharedPreferences(context);
                url = sh.getString("url","") + "/and_libraryrequest";//it not going any other pages

                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                com.android.volley.toolbox.StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                        Toast.makeText(context, "requested successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context.getApplicationContext(),view_library.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);

                                    }


                                    // }
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
                        sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("lib_id",lib_id[a]);
                        params.put("lid", sh.getString("lid", ""));//passing to python

//                        params.put("uid",sh.getString("uid",""));//user id
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


//        tv1.setText(library[i]);
        tv2.setText(name[i]);
        tv3.setText(place[i]);
        tv4.setText(phone[i]);
        tv5.setText(email[i]);
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