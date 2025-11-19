package com.devs.frutybot.presentation.notifications;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devs.frutybot.NotificationsViewModel;
import com.devs.frutybot.R;
import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.dto.RequestItemDto;

public class RequestDetailFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private TextView txtFruitName;
    private TextView txtStatus;
    private TextView txtDetails;

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
        txtStatus    = view.findViewById(R.id.txtStatus);
        txtDetails   = view.findViewById(R.id.txtDetails); // Añade este TextView en el layout para mostrar todos los campos

        String requestId = getArguments() != null ? getArguments().getString("requestId") : null;
        if (TextUtils.isEmpty(requestId)) {
            Toast.makeText(requireContext(), "No se recibió requestId", Toast.LENGTH_SHORT).show();
            return;
        }

        // ViewModel compartido con la actividad para leer la lista y el seleccionado
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);
        notificationsViewModel.selectRequest(requestId);

        // Observa el request seleccionado y renderiza
        notificationsViewModel.getSelectedRequest().observe(getViewLifecycleOwner(), item -> {
            if (item == null) return;

            txtStatus.setText("Estado: " + item.getStatus());

            FruitDto fruit = item.getFruit();
            if (fruit == null) {
                // Aún procesando: muestra placeholders
                txtFruitName.setText("Fruta: procesando…");
                txtDetails.setText("Aún no hay información disponible. Vuelve en unos segundos.");
                return;
            }

            // Renderizar información completa de la fruta
            txtFruitName.setText("Fruta: " + safe(fruit.getNombre()));

            String vitaminas = (fruit.getVitaminas() != null && !fruit.getVitaminas().isEmpty())
                    ? TextUtils.join(", ", fruit.getVitaminas())
                    : "no disponible";

            String details =
                    "País: " + safe(fruit.getPais()) + "\n" +
                            "Departamento: " + safe(fruit.getDepartamento()) + "\n" +
                            "Descripción: " + safe(fruit.getDescripcion()) + "\n" +
                            "Proceso de maduración: " + safe(fruit.getProcesoDeMaduracion()) + "\n" +
                            "Información nutricional: " + safe(fruit.getInformacionNutricional()) + "\n" +
                            "Calorías: " + safe(fruit.getCalorias()) + "\n" +
                            "Vitaminas: " + vitaminas + "\n" +
                            "Fibra: " + safe(fruit.getFibra()) + "\n" +
                            "Azúcares: " + safe(fruit.getAzucares()) + "\n" +
                            "Temporada: " + safe(fruit.getTemporada()) + "\n" +
                            "Tipo: " + safe(fruit.getTipo()) + "\n" +
                            "Válida: " + safe(fruit.getValida());

            txtDetails.setText(details);
        });
    }

    private String safe(String s) {
        return s == null || s.isEmpty() ? "no disponible" : s;
    }
}
