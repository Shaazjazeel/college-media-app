package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText name,username,pass,confirmpass,depart,regno,age,place,yer,bio;
    RadioButton female,male;
    Button submit;
    ImageView im;
    String url;
    Bitmap bitmap = null;
    ProgressDialog pd;
    SharedPreferences sh;
    String gender;
    String password_pattern="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}";
    String pin_pattern="\\d{6}";
    String reg ="[A-Z]{*}[0-9]{*}";
    String phone_pattern="[6789][0-9]{9}";
    String email_pattern= "[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.com";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.pname);
        username=findViewById(R.id.unss);
        pass=findViewById(R.id.ssss);
        confirmpass=findViewById(R.id.cc);
        depart=findViewById(R.id.pdepart);
        regno=findViewById(R.id.regno);
        age=findViewById(R.id.ageee);
        female=findViewById(R.id.fffff);
        male=findViewById(R.id.mmmmm);
        place=findViewById(R.id.plvvv);
        yer=findViewById(R.id.yrrrr);
        bio=findViewById(R.id.biooooo);
        im=findViewById(R.id.imageView3);
        submit=findViewById(R.id.button3);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip","");
        url=sh.getString("url","")+"/and_register";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm = name.getText().toString();
                String us = username.getText().toString();
                String ps = pass.getText().toString();
                String cnfpass = confirmpass.getText().toString();
                String dp = depart.getText().toString();
                String rg = regno.getText().toString();
                String ag = age.getText().toString();
                String plc = place.getText().toString();
                String year = yer.getText().toString();
                String bio1 = bio.getText().toString();
                if (male.isChecked()) {
                    gender = "male";
                }
                if (female.isChecked()) {
                    gender = "female";
                }


                int flag = 0;

                // Name validation
                if (nm.isEmpty()) {
                    name.setError("Enter valid name");
                    flag++;
                }

                if (us.isEmpty()) {
                    username.setError("Enter valid username");
                    flag++;
                }

                // Phone validation
                if (dp.isEmpty()) {
                    depart.setError("Enter  department");
                    flag++;
                }

                // Post validation
                if (reg.isEmpty()) {
                    regno.setError("Enter valid Register number");
                    flag++;
                }
                if (year.isEmpty() || !year.matches("[0-9]+")) {
                    yer.setError("Enter valid year");
                    flag++;
                }

                // Pin validation
                if (ag.isEmpty() || !ag.matches("[0-9]+")) {
                    age.setError("Enter valid age");
                    flag++;
                }

                // Place validation
                if (plc.isEmpty()) {
                    place.setError("Enter valid place");
                    flag++;
                }
                if (bio1.isEmpty()) {
                    bio.setError("Enter bio");
                    flag++;
                }

                // Password validation
                if (ps.isEmpty() || ps.length() < 8) {
                    pass.setError("Password must be at least 8 characters");
                    flag++;
                } else if (!ps.equals(cnfpass)) {
                    confirmpass.setError("Passwords do not match");
                    flag++;
                }

                if (flag == 0) {

                    uploadBitmap(nm, us, ps, cnfpass, dp, rg, ag, plc, gender, bio1, year);
                }
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
    private void uploadBitmap(final String nm, final String us, final String ps, final String cnfpass, final String dp, final String rg, final String ag, final String plc, String gender, String bio1, String year) {


        pd = new ProgressDialog(register.this);
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
                                Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),login.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration failed" ,Toast.LENGTH_SHORT).show();
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
                params.put("nme",nm);//passing to python
                params.put("usrnm", us);//passing to python
                params.put("age", ag);
                params.put("place", plc);
                params.put("regno", rg);
                params.put("pass", ps);
                params.put("cnfps", cnfpass);
                params.put("gender", gender);
                params.put("depart", dp);
                params.put("bio",bio1);
                params.put("year",year);




                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}