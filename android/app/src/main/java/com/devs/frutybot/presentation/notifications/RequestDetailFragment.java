package com.devs.frutybot.presentation.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devs.frutybot.MainActivity;
import com.devs.frutybot.R;
import com.devs.frutybot.data.ws.WsManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestDetailFragment extends Fragment {

    private RequestDetailViewModel viewModel;
    TextView txtFruitName ;
    TextView txtStatus;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String requestId = getArguments() != null ? getArguments().getString("requestId") : null;
        if (requestId != null) {
            viewModel.subscribeToResult(requestId);
        } else {
            Toast.makeText(requireContext(), "No se recibió requestId", Toast.LENGTH_SHORT).show();
        }

        // Obtener el WsManager desde la actividad
        WsManager wsManager = ((MainActivity) requireActivity()).getWsManager();
        txtFruitName = view.findViewById(R.id.txtFruitName);
        txtStatus = view.findViewById(R.id.txtStatus);

        // Crear el ViewModel con el factory
        RequestDetailViewModel viewModel = new ViewModelProvider(
                this,
                new RequestDetailViewModelFactory(wsManager)
        ).get(RequestDetailViewModel.class);

        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), result -> {
            try {
                JSONObject obj = new JSONObject(result);
                JSONObject res = obj.getJSONObject("result");

                txtFruitName.setText("Fruta: " + res.optString("fruitName"));
                txtStatus.setText("Estado: " + res.optString("status"));
            } catch (JSONException e) {
                Toast.makeText(requireContext(), "Error parseando", Toast.LENGTH_SHORT).show();
            }
        });



        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_LONG).show();
        });


        viewModel.subscribeToResult(requestId);
    }

}

