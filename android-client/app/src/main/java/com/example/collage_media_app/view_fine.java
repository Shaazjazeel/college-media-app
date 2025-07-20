package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_fine extends AppCompatActivity {
    String[] fine_id,f_image,f_lib,f_name,f_author,f_bdate,f_ddate,f_rdate,f_days,f_status,f_amount;
    ListView li;
    SharedPreferences sh;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fine);
        li=findViewById(R.id.ineeeeee);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip", "");
        url = sh.getString("url", "") + "/and_viewfine";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("data");


                                fine_id=new String[js.length()];
                                f_lib=new String[js.length()];
                                f_image=new String[js.length()];
                                f_name=new String[js.length()];
                                f_author=new String[js.length()];
                                f_bdate=new String[js.length()];
                                f_ddate=new String[js.length()];
                                f_rdate=new String[js.length()];
                                f_days=new String[js.length()];
                                f_status=new String[js.length()];
                                f_rdate=new String[js.length()];
                                f_amount=new String[js.length()];
//                                JSONArray js1= jsonObj.getJSONArray("rating");
//                                rating=new String[js1.length()];

                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    fine_id[i]=u.getString("fine_id");
                                    f_lib[i]=u.getString("f_lib");
                                    f_image[i]=u.getString("f_image");
                                    f_name[i]=u.getString("f_name");
                                    f_author[i]=u.getString("f_author");
                                    f_bdate[i]=u.getString("f_bdate");
                                    f_ddate[i]=u.getString("f_ddate");
                                    f_days[i]=u.getString("f_days");
                                    f_rdate[i]=u.getString("f_rdate");
                                    f_amount[i]=u.getString("f_amount");

                                }
//                                for(int i=0;i<js1.length();i++)
//                                {
//                                    JSONObject u=js1.getJSONObject(i);
//                                    rating[i]=u.getString("rating");
//
//                                }

                                // ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
                                li.setAdapter(new custom_view_fine(getApplicationContext(),fine_id,f_lib,f_image,f_name,f_author,f_bdate,f_ddate,f_rdate,f_days,f_status,f_amount));
                                // l1.setAdapter(new Custom(getApplicationContext(),gamecode,name,type,discription,image,status));
                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

//                String lid=sh.getString("id","");
//                params.put("id",lid);
                params.put("bb_id",sh.getString("bb_id",""));
                params.put("lid",sh.getString("lid",""));
//                params.put("mac",maclis);

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
}