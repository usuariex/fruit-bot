package com.devs.frutybot.presentation.catalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.R;
import com.devs.frutybot.presentation.adapters.FruitAdapter;


public class CatalogFragment extends Fragment {

    private CatalogViewModel viewModel;
    private FruitAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rvCatalog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FruitAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CatalogViewModel.class);

        // Recibir argumento del departamento
        String departamento = getArguments().getString("department", "");

        // Observar frutas
        viewModel.getFrutas().observe(getViewLifecycleOwner(), frutas -> {
            adapter.setFrutas(frutas);
        });

        // Observar errores
        viewModel.getError().observe(getViewLifecycleOwner(), errorMsg -> {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        });

        // Cargar frutas
        viewModel.loadFruits(departamento);
    }
}
