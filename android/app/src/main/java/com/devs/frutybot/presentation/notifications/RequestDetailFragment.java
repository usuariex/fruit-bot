package com.devs.frutybot.presentation.notifications;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.devs.frutybot.R;
import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.NotificationsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RequestDetailFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private TextView txtFruitName;
    private TextView txtStatus;
    private TextView txtDetails;
    private ImageView imgFruitDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtFruitName = view.findViewById(R.id.txtFruitName);
        txtStatus = view.findViewById(R.id.txtStatus);
        txtDetails = view.findViewById(R.id.txtDetails);
        imgFruitDetail = view.findViewById(R.id.imgFruitDetail);

        String requestId = getArguments() != null ? getArguments().getString("requestId") : null;
        if (TextUtils.isEmpty(requestId)) {
            Toast.makeText(requireContext(), "No se recibió requestId", Toast.LENGTH_SHORT).show();
            return;
        }

        // ViewModel compartido que ya tiene la data actualizada via WebSocket
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);

        notificationsViewModel.selectRequest(requestId);
        notificationsViewModel.getSelectedRequest().observe(getViewLifecycleOwner(), item -> {
            if (item == null) return;

            txtStatus.setText("Estado: " + safe(item.getStatus()));
            
            // Renderizar imagen
            if (item.getPhotoPath() != null && !item.getPhotoPath().isEmpty()) {
                Glide.with(this)
                        .load(item.getPhotoPath())
                        .placeholder(R.drawable.ic_fruit_placeholder)
                        .error(R.drawable.ic_fruit_placeholder)
                        .centerCrop()
                        .into(imgFruitDetail);
            } else {
                imgFruitDetail.setImageResource(R.drawable.ic_fruit_placeholder);
            }

            FruitDto fruit = item.getFruit();
            if (fruit == null) {
                txtFruitName.setText("Fruta: Procesando...");
                txtDetails.setText("Analizando imagen. Espera un momento...");
            } else {
                renderFruit(fruit);
            }
        });
    }

    private void renderFruit(FruitDto fruit) {
        txtFruitName.setText("Fruta: " + safe(fruit.getNombre()));

        String vitaminas = (fruit.getVitaminas() != null && !fruit.getVitaminas().isEmpty())
                ? TextUtils.join(", ", fruit.getVitaminas())
                : "no disponible";

        String details =
                "País: " + safe(fruit.getPais()) + "\n\n" +
                "Departamento: " + safe(fruit.getDepartamento()) + "\n\n" +
                "Descripción: " + safe(fruit.getDescripcion()) + "\n\n" +
                "Proceso de maduración: " + safe(fruit.getProcesoDeMaduracion()) + "\n\n" +
                "Información nutricional: " + safe(fruit.getInformacionNutricional()) + "\n\n" +
                "Calorías: " + safe(fruit.getCalorias()) + "\n\n" +
                "Vitaminas: " + vitaminas + "\n\n" +
                "Fibra: " + safe(fruit.getFibra()) + "\n\n" +
                "Azúcares: " + safe(fruit.getAzucares()) + "\n\n" +
                "Temporada: " + safe(fruit.getTemporada()) + "\n\n" +
                "Tipo: " + safe(fruit.getTipo()) + "\n\n" +
                "Válida: " + safe(fruit.getValida());

        txtDetails.setText(details);
    }

    private String safe(String s) {
        return s == null || s.isEmpty() ? "no disponible" : s;
    }
}
