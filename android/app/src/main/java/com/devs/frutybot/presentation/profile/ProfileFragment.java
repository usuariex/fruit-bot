package com.devs.frutybot.presentation.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.devs.frutybot.R;

public class ProfileFragment extends Fragment {

    ProfileViewModel viewModel;

    ImageView imgProfile;
    TextView tvUsername;
    Button btnLogin, btnEditName, btnSaveName, btnChangePhoto;
    EditText etNewName;
    Button btnLogout;

    ActivityResultLauncher<Intent> galleryLauncher;

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

        imgProfile = view.findViewById(R.id.imgProfile);
        tvUsername = view.findViewById(R.id.tvUsername);
        btnLogin = view.findViewById(R.id.btnLoginProfile);
        btnChangePhoto = view.findViewById(R.id.btnChangePhoto);
        btnEditName = view.findViewById(R.id.btnEditUsername);
        btnSaveName = view.findViewById(R.id.btnSaveUsername);
        etNewName = view.findViewById(R.id.etNewUsername);
        btnLogout = view.findViewById(R.id.btnLogout);

        setupGalleryPicker();

        observeData();

        btnLogin.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_loginFragment)
        );

        btnEditName.setOnClickListener(v -> {
            etNewName.setVisibility(View.VISIBLE);
            btnSaveName.setVisibility(View.VISIBLE);
        });

        btnSaveName.setOnClickListener(v -> {
            String newName = etNewName.getText().toString();
            if (!newName.isEmpty()) {
                viewModel.updateUsername(newName);
                etNewName.setVisibility(View.GONE);
                btnSaveName.setVisibility(View.GONE);
            }
        });

        btnChangePhoto.setOnClickListener(v -> {
            Intent pick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(pick);
        });
        btnLogout.setOnClickListener(v -> {
            viewModel.logout(); // Limpiamos sesión
        });

    }

    private void setupGalleryPicker() {
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri imgUri = result.getData().getData();
                        viewModel.updatePhoto(imgUri.toString());
                    }
                }
        );
    }

    private void observeData() {

        viewModel.isLoggedIn.observe(getViewLifecycleOwner(), loggedIn -> {
            if (loggedIn) {
                btnLogin.setVisibility(View.GONE);
                btnEditName.setVisibility(View.VISIBLE);
                btnChangePhoto.setVisibility(View.VISIBLE);
                tvUsername.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.VISIBLE);
            } else {
                btnLogin.setVisibility(View.VISIBLE);
                btnEditName.setVisibility(View.GONE);
                btnChangePhoto.setVisibility(View.GONE);
                etNewName.setVisibility(View.GONE);
                btnSaveName.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE);
                tvUsername.setText("prueba");
                imgProfile.setImageResource(R.drawable.circle_background);

                // Navegar automáticamente al login
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });


        viewModel.username.observe(getViewLifecycleOwner(), name ->
                tvUsername.setText(name)
        );

        viewModel.profilePhoto.observe(getViewLifecycleOwner(), uri -> {
            if (uri != null)
                imgProfile.setImageURI(Uri.parse(uri));
            else
                imgProfile.setImageResource(R.drawable.circle_background);
        });
        viewModel.isLoggedIn.observe(getViewLifecycleOwner(), loggedIn -> {
            if (loggedIn) {
                btnLogin.setVisibility(View.GONE);
                btnEditName.setVisibility(View.VISIBLE);
                btnChangePhoto.setVisibility(View.VISIBLE);
                tvUsername.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.VISIBLE); // MOSTRAR botón logout
            } else {
                btnLogin.setVisibility(View.VISIBLE);
                btnEditName.setVisibility(View.GONE);
                btnChangePhoto.setVisibility(View.GONE);
                etNewName.setVisibility(View.GONE);
                btnSaveName.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE); // OCULTAR botón logout
                tvUsername.setText("Invitado");
                imgProfile.setImageResource(R.drawable.circle_background);
            }
        });

    }
}
