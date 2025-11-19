package com.devs.frutybot.presentation.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.devs.frutybot.R;
import com.devs.frutybot.data.auth.MockAuthRepository;
import com.devs.frutybot.data.local.UserSession;
import com.devs.frutybot.data.user.UserDto;
import com.devs.frutybot.presentation.profile.ProfileViewModel;

public class LoginFragment extends Fragment {

    private EditText etUser, etPass;
    private Button btnLogin;
    private ProfileViewModel viewModel;
    private UserSession userSession;
    private MockAuthRepository authRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        userSession = new UserSession(requireContext());
        authRepo = new MockAuthRepository(); // mock repository

        etUser = view.findViewById(R.id.etUsername);
        etPass = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLoginConfirm);

        btnLogin.setOnClickListener(v -> {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            // Usar MockAuthRepository para simular verificación
            UserDto logged = authRepo.login(user, pass);
            if (logged != null) {
                // Guardar sesión localmente usando la API de UserSession existente
                userSession.saveLogin(logged.getUsername());
                if (logged.getAvatarUrl() != null) {
                    userSession.saveProfilePhoto(logged.getAvatarUrl());
                }
                // Actualizar ViewModel (mantiene LiveData)
                viewModel.login(logged.getUsername());

                Toast.makeText(requireContext(), "¡Login exitoso!", Toast.LENGTH_SHORT).show();

                // Navegar a Home y limpiar back stack con la action definida en el nav_graph
                Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_homeFragment);
            } else {
                Toast.makeText(requireContext(), "Credenciales inválidas (mock)", Toast.LENGTH_SHORT).show();
            }
        });

        TextView tvRegister = view.findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment)
        );
    }
}
