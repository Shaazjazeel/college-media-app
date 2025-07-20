package com.example.collage_media_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ip;
    Button b1;
    String url;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip=findViewById(R.id.editTextTextPersonName18);
        b1=findViewById(R.id.button11);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = ip.getText().toString();

                int flag = 0;
                if (i.equalsIgnoreCase("")) {
                    ip.setError("required");
                    flag++;
                }
                if (flag == 0) {

                    String url = "http://" + i + ":1234";
                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("url", url);
                    ed.putString("ip", i);
//                    Toast.makeText(getApplicationContext(), "url" + url, Toast.LENGTH_LONG).show();
                    ed.commit();
                    Intent j = new Intent(getApplicationContext(), login.class);
                    startActivity(j);
                }
            }
        });
    }
}