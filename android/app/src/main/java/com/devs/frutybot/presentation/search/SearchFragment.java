package com.devs.frutybot.presentation.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.frutybot.R;
import com.devs.frutybot.presentation.adapters.FruitAdapter;

public class SearchFragment extends Fragment {

    private EditText etSearch;
    private RecyclerView rvFruits;
    private FruitAdapter adapter;
    private SearchViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Views
        etSearch = view.findViewById(R.id.etSearch);
        rvFruits = view.findViewById(R.id.rvFruits);

        // RecyclerView + Adapter
        adapter = new FruitAdapter();
        rvFruits.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvFruits.setAdapter(adapter);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Observer que actualiza el recyclerview
        viewModel.fruits.observe(getViewLifecycleOwner(), fruits -> {
            adapter.setFrutas(fruits); // Ya existe, no se duplica
        });

        // Listener del buscador
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();
                viewModel.fetchFruits(query); // ← hace la petición al endpoint /buscar
            }
        });
    }
}
