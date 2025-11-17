package com.devs.frutybot;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.devs.frutybot.data.ws.WsManager;
import com.devs.frutybot.presentation.notifications.NotificationsDialogFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private WsManager wsManager;

    @OptIn(markerClass = ExperimentalBadgeUtils.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar WebSocket global
        wsManager = new WsManager();
        wsManager.connect("ws://192.168.100.176:3020/ws");

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
        NavigationUI.setupWithNavController(bottomNav, navController);



        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            int destId = destination.getId();

            if (destId == R.id.loginFragment
                    || destId == R.id.registerFragment
                    || destId == R.id.splashFragment) {

                toolbar.setVisibility(View.GONE);

            } else {

                toolbar.setVisibility(View.VISIBLE);
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }

    public WsManager getWsManager() {
        return wsManager;
    }
}
