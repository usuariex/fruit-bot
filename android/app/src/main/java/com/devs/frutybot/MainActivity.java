package com.devs.frutybot;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.devs.frutybot.data.local.UserSession;
import com.devs.frutybot.data.ws.WsManager;
import com.devs.frutybot.presentation.notifications.NotificationsDialogFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@ExperimentalBadgeUtils
public class MainActivity extends AppCompatActivity {

    @Inject
    WsManager wsManager;

    private BadgeDrawable notificationBadge;
    private MaterialToolbar toolbar;

    @Override
    @ExperimentalBadgeUtils
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // wsManager ya está inyectado y conectado en AppModule
        // (o puedes llamar a connect aquí si prefieres manejar el ciclo de vida manual)
        // wsManager.connect(...) ya se llamó en AppModule si así se configuró,
        // o si no, lo conectamos aquí usando config inyectada.
        // Asumiendo que AppModule lo configuró:
        // wsManager.connect("ws://" + Config.BASE_URL + ":3020/ws"); <-- Esto ahora lo maneja DI preferiblemente

        // Toolbar
        toolbar = findViewById(R.id.top_app_bar);
        if (toolbar != null) {
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_notifications) {
                    NotificationsDialogFragment dialog = new NotificationsDialogFragment();
                    if (!getSupportFragmentManager().isStateSaved()) {
                        dialog.show(getSupportFragmentManager(), "NotificationsDialog");
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .add(dialog, "NotificationsDialog")
                                .commitAllowingStateLoss();
                    }
                    return true;
                }
                return false;
            });
        }

        // NavController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null) return;
        NavController navController = navHostFragment.getNavController();

        // Bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

        // Notifications ViewModel
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        // Observe requests -> update badge
        notificationsViewModel.getRequests().observe(this, requests -> {
            try {
                if (requests == null || requests.isEmpty()) {
                    if (notificationBadge != null && toolbar != null) {
                        try {
                            BadgeUtils.detachBadgeDrawable(notificationBadge, toolbar, R.id.action_notifications);
                        } catch (Exception ignored) {}
                        notificationBadge = null;
                    }
                    return;
                }

                if (notificationBadge == null) {
                    notificationBadge = BadgeDrawable.create(this);
                }

                notificationBadge.setNumber(requests.size());
                notificationBadge.setVisible(true);

                if (toolbar != null) {
                    try {
                        BadgeUtils.attachBadgeDrawable(notificationBadge, toolbar, R.id.action_notifications);
                    } catch (Exception ignored) {}
                }
            } catch (Exception ignored) {}
        });

        // Session-aware navigation for profile
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
            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        // Show/hide bottom nav and notification menu item per destination
        navController.addOnDestinationChangedListener((controller, destination, args) -> {
            int destId = destination.getId();
            boolean showBottom = (destId == R.id.homeFragment
                    || destId == R.id.searchFragment
                    || destId == R.id.scannerFragment);

            if (bottomNav != null) bottomNav.setVisibility(showBottom ? android.view.View.VISIBLE : android.view.View.GONE);

            // Show/hide menu item safely
            if (toolbar != null) {
                MenuItem notifItem = toolbar.getMenu() != null ? toolbar.getMenu().findItem(R.id.action_notifications) : null;
                if (notifItem != null) {
                    notifItem.setVisible(showBottom);
                }
            }
        });
    }

    @Override
    @ExperimentalBadgeUtils
    protected void onDestroy() {
        super.onDestroy();

        if (notificationBadge != null && toolbar != null) {
            try {
                BadgeUtils.detachBadgeDrawable(notificationBadge, toolbar, R.id.action_notifications);
            } catch (Exception ignored) {}
            notificationBadge = null;
        }
        
        // Dejamos que DI maneje el lifecycle de wsManager singleton o lo cerramos si es necesario
        if (wsManager != null) {
            wsManager.disconnect();
        }
    }
    
    public WsManager getWsManager() {
        return wsManager;
    }
}
