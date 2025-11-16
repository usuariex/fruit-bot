package com.devs.frutybot.presentation.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.widget.TextView;

import com.devs.frutybot.R;
import com.devs.frutybot.presentation.profile.ProfileViewModel;

public class LoginFragment extends Fragment {

    EditText etUser, etPass;
    Button btnLogin;

    ProfileViewModel viewModel;

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

        etUser = view.findViewById(R.id.etUsername);
        etPass = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLoginConfirm);

        btnLogin.setOnClickListener(v -> {
            String user = etUser.getText().toString();
            String pass = etPass.getText().toString();

            if (user.equals("admin") && pass.equals("1234")) {

                viewModel.login(user);

                Toast.makeText(requireContext(), "¡Login exitoso!", Toast.LENGTH_SHORT).show();

                Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_profileFragment);

            } else {
                Toast.makeText(requireContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
        TextView tvRegister = view.findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v -> {
            // Navegar al fragment de registro
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });

    }
}
