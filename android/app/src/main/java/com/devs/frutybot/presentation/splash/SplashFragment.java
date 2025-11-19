package com.devs.frutybot.presentation.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.devs.frutybot.R;
import com.devs.frutybot.data.local.UserSession;

public class SplashFragment extends Fragment {

    private static final long SPLASH_DURATION = 2000; // 2 segundos

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTheme(R.style.Theme_FrutyBot);

        UserSession session = new UserSession(requireContext());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            NavController navController = Navigation.findNavController(view);
            if (session.isLoggedIn()) {
                navController.navigate(R.id.action_splashFragment_to_homeFragment);
            } else {
                navController.navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, SPLASH_DURATION);

    }
}
