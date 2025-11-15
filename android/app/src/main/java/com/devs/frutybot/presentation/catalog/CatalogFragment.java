package com.devs.frutybot.presentation.catalog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.R;

public class CatalogFragment extends Fragment {

    private CatalogViewModel viewModel;
    private CatalogAdapter adapter;

    public CatalogFragment() {
        super(R.layout.fragment_catalog); // tu XML
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencia al RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvCatalog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configurar el adapter
        adapter = new CatalogAdapter();
        recyclerView.setAdapter(adapter);

        // Instanciar el ViewModel
        viewModel = new ViewModelProvider(this).get(CatalogViewModel.class);

        // Observar los datos
        viewModel.getFruits().observe(getViewLifecycleOwner(), fruits -> {
            adapter.submitList(fruits);
        });

        // Cargar frutas desde el repositorio
        viewModel.loadFruits();
    }
}