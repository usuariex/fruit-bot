package com.devs.frutybot.presentation.notifications;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

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
    private NotificationsAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View content = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_notifications_container, null);
        RecyclerView recyclerView = content.findViewById(R.id.recyclerNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new NotificationsAdapter(item -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            Bundle args = new Bundle();
            args.putString("requestId", item.getRequestId());
            navController.navigate(R.id.requestDetailFragment, args);
            dismiss();
        });
        recyclerView.setAdapter(adapter);

        // ViewModel compartido con la Activity
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);
        // Observa la lista usando el DialogFragment como LifecycleOwner (this)
        notificationsViewModel.getRequests().observe(this, adapter::submitList);

        return new AlertDialog.Builder(requireContext())
                .setTitle("Solicitudes")
                .setView(content)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .create();
    }
}
