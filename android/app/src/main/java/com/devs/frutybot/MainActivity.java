package com.devs.frutybot;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.devs.frutybot.common.Config;
import com.devs.frutybot.data.local.UserSession;
import com.devs.frutybot.data.ws.WsManager;
import com.devs.frutybot.presentation.notifications.NotificationsDialogFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private WsManager wsManager;
    private static final int REQ_PERM_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;


    @OptIn(markerClass = ExperimentalBadgeUtils.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        androidx.core.splashscreen.SplashScreen splashScreen =
                androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar WebSocket global
        wsManager = new WsManager();
        wsManager.connect("ws://"+ Config.BASE_URL +":3020/ws");

        // Referencia al Toolbar superior
        MaterialToolbar toolbar = findViewById(R.id.top_app_bar);

        // Listener para clicks en el menú del Toolbar
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_notifications) {
                NotificationsDialogFragment dialog = new NotificationsDialogFragment();
                dialog.show(getSupportFragmentManager(), "NotificationsDialog");
                return true;
            }
            return false;
        });


        // ViewModel compartido para notificaciones
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        // Observar solicitudes y actualizar badge en la campanita
        notificationsViewModel.getRequests().observe(this, requests -> {
            BadgeDrawable badge = BadgeDrawable.create(this);
            badge.setNumber(requests.size());
            badge.setVisible(true);

            // Adjuntar badge al ítem de la campanita del Toolbar
            BadgeUtils.attachBadgeDrawable(badge, toolbar, R.id.action_notifications);
        });

        // Configurar navegación inferior
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

// Obtener ubicación automáticamente al iniciar
        if (checkLocationPermission()) {
            getLastLocation();
        } else {
            requestLocationPermission();
        }

    }

    public WsManager getWsManager() {
        return wsManager;
    }
    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERM_LOCATION);
    }

    public void getLastLocation() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (lm != null && !lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) return;

        try {
            LocationRequest locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(0)
                    .setFastestInterval(0)
                    .setNumUpdates(1);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) return;

            fusedLocationClient.requestLocationUpdates(locationRequest, new com.google.android.gms.location.LocationCallback() {
                @Override
                public void onLocationResult(@NonNull com.google.android.gms.location.LocationResult locationResult) {
                    fusedLocationClient.removeLocationUpdates(this);
                    if (locationResult.getLastLocation() != null) {
                        Location location = locationResult.getLastLocation();
                        // Aquí puedes usar location.getLatitude() y location.getLongitude()
                        // por ejemplo para enviar al servidor o guardar localmente
                    }
                }
            }, getMainLooper());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERM_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

}
