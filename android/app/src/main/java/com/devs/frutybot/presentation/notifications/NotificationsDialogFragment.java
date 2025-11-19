package com.devs.frutybot.presentation.notifications;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.NotificationsViewModel;
import com.devs.frutybot.R;
import com.devs.frutybot.presentation.adapters.NotificationsAdapter;

public class NotificationsDialogFragment extends DialogFragment {

    private NotificationsViewModel notificationsViewModel;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // RecyclerView para la lista
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // ViewModel compartido con la Activity (donde vive el NavHost)
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);

        // NavController desde el NavHostFragment de la Activity
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        // Adapter con callback de click: navega al detalle y cierra el diálogo
        NotificationsAdapter adapter = new NotificationsAdapter(item -> {
            Bundle args = new Bundle();
            args.putString("requestId", item.getRequestId());
            navController.navigate(R.id.requestDetailFragment, args);
            dismiss();
        });
        recyclerView.setAdapter(adapter);

        // Observar la lista de solicitudes y renderizar
        notificationsViewModel.getRequests().observe(getViewLifecycleOwner(), adapter::submitList);

        return recyclerView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Usa la vista creada arriba como contenido del diálogo
        View content = onCreateView(getLayoutInflater(), null, savedInstanceState);
        return new AlertDialog.Builder(requireContext())
                .setTitle("Solicitudes")
                .setView(content)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .create();
    }
}

