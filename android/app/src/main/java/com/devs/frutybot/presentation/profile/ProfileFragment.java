package com.devs.frutybot.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.devs.frutybot.R;
import com.devs.frutybot.data.local.UserSession;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private UserSession userSession;
    private TextView txtUsername;
    private Button btnLogout;
    private ImageView imgAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        userSession = new UserSession(requireContext());

        txtUsername = view.findViewById(R.id.tvUsername);
        btnLogout = view.findViewById(R.id.btnLogout);
        imgAvatar = view.findViewById(R.id.imgProfile);

        // Mostrar usuario desde sesión o ViewModel inicial
        String username = userSession.getUsername();
        String avatar = userSession.getProfilePhoto();

        txtUsername.setText(username != null ? username : "Usuario");
        if (avatar != null && !avatar.isEmpty()) {
            // Si usas una librería para imágenes (Glide/Picasso), cámbialo aquí.
            // Ejemplo con Glide (si tienes la dependencia):
            // Glide.with(this).load(avatar).placeholder(R.drawable.circle_background).into(imgAvatar);
        } else {
            imgAvatar.setImageResource(R.drawable.circle_background);
        }

        // Observadores del ViewModel (actualizan UI cuando cambian los LiveData)
        viewModel.username.observe(getViewLifecycleOwner(), name -> {
            txtUsername.setText(name != null ? name : "Usuario");
        });
        viewModel.profilePhoto.observe(getViewLifecycleOwner(), uri -> {
            if (uri != null && !uri.isEmpty()) {
                // Glide.with(this).load(uri).placeholder(R.drawable.circle_background).into(imgAvatar);
            } else {
                imgAvatar.setImageResource(R.drawable.circle_background);
            }
        });
        viewModel.isLoggedIn.observe(getViewLifecycleOwner(), logged -> {

            btnLogout.setVisibility((logged != null && logged) ? View.VISIBLE : View.GONE);
        });

        btnLogout.setOnClickListener(v -> {

            userSession.logout();
            viewModel.logout();

            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });
    }
}
