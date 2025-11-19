package com.devs.frutybot.presentation.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.devs.frutybot.R;
import com.devs.frutybot.data.dto.FruitDto;

public class DetailsFragment extends Fragment {

    private DetailsViewModel viewModel;
    private ImageView imageView;
    private TextView textViewNombre, textViewDescripcion, textViewDeptoNombre, textViewDeptoDescripcion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        imageView = view.findViewById(R.id.imageView);
        textViewNombre = view.findViewById(R.id.textViewNombre);
        textViewDescripcion = view.findViewById(R.id.textViewDescripcion);
        textViewDeptoNombre = view.findViewById(R.id.textViewDeptoNombre);
        textViewDeptoDescripcion = view.findViewById(R.id.textViewDeptoDescripcion);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);

        viewModel.getFruit().observe(getViewLifecycleOwner(), this::bindData);

        // Carga frutas del departamento "Lima" como ejemplo
        viewModel.loadFruitByDepartment("Lima");
    }

    private void bindData(FruitDto fruit) {
        textViewNombre.setText(fruit.getNombre());
        textViewDescripcion.setText(fruit.getDescripcion());
        textViewDeptoNombre.setText(fruit.getDepartamentoNombre());
        textViewDeptoDescripcion.setText(fruit.getDepartamentoDescripcion());

        Glide.with(requireContext())
                .load(fruit.getImagen())
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView);
    }
}
