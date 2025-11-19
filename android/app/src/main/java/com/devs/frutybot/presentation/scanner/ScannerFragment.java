package com.devs.frutybot.presentation.scanner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import com.devs.frutybot.MainActivity;
import com.devs.frutybot.NotificationsViewModel;
import com.devs.frutybot.R;
import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.dto.RequestItemDto;
import com.devs.frutybot.data.ws.WsManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ScannerFragment extends Fragment {

    private PreviewView previewView;
    private ImageCapture imageCapture;
    private ScannerViewModel viewModel;
    private EditText inputText;
    private NotificationsViewModel notificationsViewModel;

    public ScannerFragment() {
        super(R.layout.fragment_scanner);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        previewView = view.findViewById(R.id.previewView);
        inputText = view.findViewById(R.id.inputText);
        Button btnUpload = view.findViewById(R.id.btnUpload);

        // ViewModel compartido para notificaciones
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);





        viewModel = new ViewModelProvider(this).get(ScannerViewModel.class);

        viewModel.getFruitInfo().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRequestId() != null) {
                // Crear RequestItemDto inicial con datos del UploadResponseStart
                RequestItemDto item = new RequestItemDto(
                        response.getRequestId(),
                        response.getStatus() != null ? response.getStatus() : "Procesando",
                        response.getImageUrl()
                );
                notificationsViewModel.addRequest(item);

                // Suscribirse al WebSocket usando el requestId
                WsManager wsManager = ((MainActivity) requireActivity()).getWsManager();
                wsManager.subscribe(response.getRequestId(), new WsManager.WsCallback() {
                    @Override
                    public void onResult(String requestId, String json) {
                        try {
                            JSONObject obj = new JSONObject(json);
                            String status = obj.optString("status");

                            if ("done".equals(status)) {
                                JSONObject fruitObj = obj.getJSONObject("fruit");
                                FruitDto fruit = new Gson().fromJson(fruitObj.toString(), FruitDto.class);

                                notificationsViewModel.updateRequest(requestId, "Listo", fruit);
                            } else if ("error".equals(status)) {
                                String errorMsg = obj.optString("error");
                                notificationsViewModel.updateRequest(requestId, "Error", null);
                                Toast.makeText(requireContext(), "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            notificationsViewModel.updateRequest(requestId, "Error", null);
                        }
                    }

                    @Override
                    public void onError(String requestId, String error) {
                        notificationsViewModel.updateRequest(requestId, "Error", null);
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Error al subir la foto", Toast.LENGTH_SHORT).show();
            }
        });







        TextView hintText = view.findViewById(R.id.hintText);
        TextInputLayout textInputLayout = view.findViewById(R.id.textInputLayout);

        hintText.setOnClickListener(v -> {
            // Ocultar el texto inicial
            hintText.setVisibility(View.GONE);
            // Mostrar el campo de texto
            textInputLayout.setVisibility(View.VISIBLE);
            // Dar foco al input
            if (textInputLayout.getEditText() != null) {
                textInputLayout.getEditText().requestFocus();

                // Abrir teclado automáticamente
                InputMethodManager imm = (InputMethodManager) requireContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(textInputLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
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
                    Toast.makeText(requireContext(), "El permiso de la cámara es necesario", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(requireContext(), "Error al iniciar la cámara", Toast.LENGTH_SHORT).show();
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

                        new AlertDialog.Builder(requireContext())
                                .setTitle("Solicitud enviada")
                                .setMessage("Su solicitud está siendo procesada. Revisa la campanita.")
                                .setPositiveButton("Aceptar", (dialog, which) -> {
                                    dialog.dismiss();
                                    // Navegar al HomeFragment cuando el usuario cierre el diálogo
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                                    navController.navigate(R.id.homeFragment);
                                })
                                .setCancelable(false)
                                .show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(
                                requireContext(),
                                "Error al tomar la foto: " + exception.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

}
