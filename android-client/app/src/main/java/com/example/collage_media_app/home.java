package com.example.collage_media_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collage_media_app.databinding.ActivityHomeBinding;

public class home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.prof) {
                    Intent i = new Intent(getApplicationContext(),profile.class);
                    startActivity(i);
                }

                else if (id == R.id.postt) {
                    Intent i = new Intent(getApplicationContext(), viewpost.class);
                    startActivity(i);
                }   else if (id == R.id.requesttt) {
                    Intent i = new Intent(getApplicationContext(), view_friend_request_and_verify.class);
                    startActivity(i);
                }


                else if(id==R.id.comm){
                    Intent i=new Intent(getApplicationContext(),view_community.class);
                    startActivity(i);
                }

//                else if(id==R.id.others_community){
//                    Intent i=new Intent(getApplicationContext(),view_others_community.class);
//                    startActivity(i);
//                }
                else if(id==R.id.chng){
                    Intent i=new Intent(getApplicationContext(),change_password.class);
                    startActivity(i);
                }
                else if(id==R.id.feeedd){
                    Intent i=new Intent(getApplicationContext(),sendfeedback.class);
                    startActivity(i);

                }
                else if(id==R.id.stud){
                    Intent i=new Intent(getApplicationContext(),view_friendrequest_status.class);
                    startActivity(i);
                }
                else if(id==R.id.othstd){
                    Intent i=new Intent(getApplicationContext(),viewotheruserpost.class);
                    startActivity(i);
                }
                else if(id==R.id.librr){
                    Intent i=new Intent(getApplicationContext(), view_library.class);
                    startActivity(i);
                }
                else if(id==R.id.membr){
                    Intent i=new Intent(getApplicationContext(),view_membership_request_status.class);
                    startActivity(i);
                }
                else if(id==R.id.broow){
                    Intent i=new Intent(getApplicationContext(), view_history_borrowal_book.class);
                    startActivity(i);
                }
                else if(id==R.id.hist){
                    Intent i= new Intent(getApplicationContext(),view_return_history.class);
                    startActivity(i);

                }
                else if(id==R.id.finnee){
                    Intent i=new Intent(getApplicationContext(),view_fine.class);
                    startActivity(i);
                } else if(id==R.id.following){
                    Intent i= new Intent(getApplicationContext(),view_follow_list.class);
                    startActivity(i);

                }
                else if(id==R.id.followerss){
                    Intent i=new Intent(getApplicationContext(),view_follower_list.class);
                    startActivity(i);
                }

        else if(id == R.id.logout)
        {

                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.commit();
                ed.clear();
                Intent i=new Intent(getApplicationContext(),login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                startActivity(i);

        }


                return false;

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}