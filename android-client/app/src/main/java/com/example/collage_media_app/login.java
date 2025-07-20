package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity {
    TextView signup;
    EditText username,pass;
    Button login;
    String url;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.editTextTextPersonName);
        pass=findViewById(R.id.editTextTextPersonName2);
        signup=findViewById(R.id.textView2);
        login=findViewById(R.id.button);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),register.class);
                startActivity(i);

            }
        });

        url=sh.getString("url","")+"/and_login";
//        Toast.makeText(getApplicationContext(), "url"+url, Toast.LENGTH_SHORT).show();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String un = username.getText().toString();
                String pas = pass.getText().toString();
                int flag = 0;
                if (un.equalsIgnoreCase("")) {
                    username.setError("required");
                    flag++;
                }
                if (pas.equalsIgnoreCase("")) {
                    pass.setError("required");
                    flag++;
                }
                if (flag == 0) {

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
                                            String type = jsonObj.getString("type");
                                            String lid = jsonObj.getString("lid");
                                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            SharedPreferences.Editor ed = sh.edit();
                                            ed.putString("lid", lid);
                                            ed.putString("type", type);
                                            ed.commit();


                                            if (type.equalsIgnoreCase("student")) {
                                                Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent(getApplicationContext(), home.class);
                                                startActivity(i);

                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
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

                            params.put("unm", un);
                            params.put("pss", pas);

                            return params;
                        }
                    };

                    int MY_SOCKET_TIMEOUT_MS = 100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);
                }
            }
        });


    }
}