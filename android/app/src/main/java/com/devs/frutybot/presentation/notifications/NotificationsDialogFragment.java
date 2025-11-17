package com.devs.frutybot.presentation.notifications;

import android.app.Dialog;
import android.os.Bundle;
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
import com.devs.frutybot.presentation.adapters.NotificationsAdapter;
import com.devs.frutybot.R;

public class NotificationsDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Usar el ViewModel compartido
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);

        // Inflar layout con RecyclerView
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Obtener el NavController desde el host fragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        // Pasar el NavController al adapter
        NotificationsAdapter adapter = new NotificationsAdapter(navController);
        recyclerView.setAdapter(adapter);

        // Observar la lista de solicitudes
        notificationsViewModel.getRequests().observe(this, adapter::submitList);

        return new AlertDialog.Builder(requireContext())
                .setTitle("Solicitudes pendientes")
                .setView(recyclerView)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .create();
    }
}
