package com.devs.frutybot.presentation.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.devs.frutybot.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment {

    EditText etUsername, etPassword;
    Button btnLoginConfirm, btnBack;

    public LoginFragment() {
        // Constructor vacío obligatorio
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Texto de registro ---
        TextView tvRegister = view.findViewById(R.id.tvRegister);
        tvRegister.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        tvRegister.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tvRegister.setTypeface(tvRegister.getTypeface(), Typeface.BOLD);

        tvRegister.setOnClickListener(v -> {
            // Navegar al fragment de registro
            Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });

        // --- Campos y botones ---
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        btnLoginConfirm = view.findViewById(R.id.btnLoginConfirm);
        btnBack = view.findViewById(R.id.btnBack);

        btnLoginConfirm.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();

            if (user.equals("admin") && pass.equals("1234")) {
                Toast.makeText(requireContext(), "¡Login exitoso!", Toast.LENGTH_SHORT).show();

                Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_homeFragment);

            } else {
                Toast.makeText(requireContext(),
                        "Usuario o contraseña incorrecta",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
}
