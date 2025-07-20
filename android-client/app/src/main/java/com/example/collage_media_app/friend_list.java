//package com.example.collage_media_app;
//
//import android.os.Bundle;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//
//import com.example.collage_media_app.databinding.ActivityFriendListBinding;
//import com.google.android.material.navigation.NavigationView;
//
//public class friend_list extends AppCompatActivity {
//
//    private ActivityFriendListBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityFriendListBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_friend_list);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
////        navigationU.setItemIconTintList(null);
////        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//        NavigationView navigationView;
//        navigationView.setItemIconTintList(null); // Remove tint color if desired
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                Intent intent;
//                switch (id) {
//                    case R.id.prof:
//                        intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.postt:
//                        intent = new Intent(getApplicationContext(), ViewPostActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.comm:
//                        intent = new Intent(getApplicationContext(), ViewCommunityActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.chng:
//                        intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.feeedd:
//                        intent = new Intent(getApplicationContext(), SendFeedbackActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.stud:
//                        intent = new Intent(getApplicationContext(), ViewFriendRequestStatusActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.librr:
//                        intent = new Intent(getApplicationContext(), ViewLibraryActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.membr:
//                        intent = new Intent(getApplicationContext(), ViewMembershipRequestStatusActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.broow:
//                        intent = new Intent(getApplicationContext(), ViewHistoryBorrowedBookActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.hist:
//                        intent = new Intent(getApplicationContext(), ViewReturnHistoryActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.finnee:
//                        intent = new Intent(getApplicationContext(), ViewFineActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    // Handle other cases if needed
//                    default:
//                        return false;
//                }
//
//                return true;
//            }
//        });
//    }
//
//}