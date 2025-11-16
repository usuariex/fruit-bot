package com.devs.frutybot.presentation.scanner;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.devs.frutybot.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;

public class ScannerFragment extends Fragment {

    private PreviewView previewView;
    private ImageCapture imageCapture;
    private ScannerViewModel viewModel;
    private EditText inputText;

    public ScannerFragment() {
        super(R.layout.fragment_scanner);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        previewView = view.findViewById(R.id.previewView);
        inputText = view.findViewById(R.id.inputText);
        Button btnUpload = view.findViewById(R.id.btnUpload);

        viewModel = new ViewModelProvider(this).get(ScannerViewModel.class);

        viewModel.getFruitInfo().observe(getViewLifecycleOwner(), result -> {
            Toast.makeText(requireContext(), "Result: " + result, Toast.LENGTH_LONG).show();
        });

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }

        btnUpload.setOnClickListener(v -> takePhoto());
    }



    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startCamera();
                } else {
                    Toast.makeText(requireContext(), "El permiso de la camara es necesario", Toast.LENGTH_SHORT).show();
                }
            });




    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(requireView().getDisplay().getRotation())
                        .build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Error starting camera", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }





    private void takePhoto() {
        File photoFile = new File(requireContext().getCacheDir(), "capture.jpg");

        ImageCapture.OutputFileOptions outputOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(requireContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        String text = inputText.getText().toString().trim();

                        viewModel.uploadFruit(photoFile, text);

                        Toast.makeText(
                                requireContext(),
                                "Your request was sent. The response will be available soon.",
                                Toast.LENGTH_LONG
                        ).show();

                        NavController navController =
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.homeFragment);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(
                                requireContext(),
                                "Error taking photo: " + exception.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}