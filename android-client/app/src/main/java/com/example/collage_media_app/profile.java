package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {
    ImageView im;
    EditText name,depart,regno,bio,place,age,year;
    RadioButton female,male;
    String gender;
    String url;
    SharedPreferences sh;
    Button edit;
    Bitmap bitmap = null;
    ProgressDialog pd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        im=findViewById(R.id.imageView11);
        name=findViewById(R.id.nameeeee);
        depart=findViewById(R.id.depppppaaa);
        regno=findViewById(R.id.regiiiiii);
        female=findViewById(R.id.radioButton2female);
        male=findViewById(R.id.radioButtonmaleee);
        bio=findViewById(R.id.editTextTextPersonName15iooo);
        place=findViewById(R.id.ppplll);
        age=findViewById(R.id.ageeee);
        year=findViewById(R.id.poooohhh);
        edit=findViewById(R.id.upttttt);



        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"/and_viewprofile";


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
                                Toast.makeText(getApplicationContext(),jsonObj.getString("age"),Toast.LENGTH_LONG).show();
                                name.setText(jsonObj.getString("name"));
                                depart.setText(jsonObj.getString("depart"));
                                regno.setText(jsonObj.getString("regno"));
                                gender=jsonObj.getString("gender");
                                bio.setText(jsonObj.getString("bio"));
                                place.setText(jsonObj.getString("place"));
                                age.setText(jsonObj.getString("age"));
                                year.setText(jsonObj.getString("year"));
                                if(gender.equalsIgnoreCase("male")){
                                    male.setChecked(true);
                                }
                                if(gender.equalsIgnoreCase("female")){
                                    female.setChecked(true);
                                }

                                String image=jsonObj.getString("image");

                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip=sh.getString("ip","");

                                String url=sh.getString("url","")+image;


                                Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()). into(im);

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


        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"/editprofile";

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nm=name.getText().toString();

                String bo=bio.getText().toString();
                String ag=age.getText().toString();
                String dp=depart.getText().toString();
                String rg=regno.getText().toString();
                String yr=year.getText().toString();
                String plc=place.getText().toString();
                if(male.isChecked()){
                    gender="male";
                }
                if(female.isChecked()){
                    gender="female";
                }

                uploadBitmap(nm,bo,ag,dp,rg,yr,plc,gender);
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                im.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final String nm, final String bo,final String ag, final String dp,final String rg, final String yr, final String plc,final String gender) {


        pd = new ProgressDialog(profile.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "Updated  successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),home.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Updation failed" ,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("name",nm);//passing to python
                params.put("bio", bo);
                params.put("age", ag);
                params.put("depart", dp);
                params.put("reg", rg);
                params.put("year", yr);
                params.put("place", plc);
                params.put("gender", gender);//passing to python
                params.put("lid",sh.getString("lid",""));


                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if(bitmap!=null) {
                    params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}