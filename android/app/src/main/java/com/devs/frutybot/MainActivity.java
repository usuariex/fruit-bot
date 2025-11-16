package com.devs.frutybot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.devs.frutybot.data.local.UserSession;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        UserSession session = new UserSession(this);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.profileFragment) {

                if (session.isLoggedIn()) {
                    navController.navigate(R.id.profileFragment);
                } else {
                    navController.navigate(R.id.loginFragment);
                }

                return true;
            }

            return NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item);
        });

        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}