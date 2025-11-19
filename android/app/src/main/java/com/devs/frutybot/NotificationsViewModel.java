package com.devs.frutybot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.dto.RequestItemDto;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<List<RequestItemDto>> requests = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> selectedRequestId = new MutableLiveData<>();

    // Exponer la lista completa para renderizar las notificaciones
    public LiveData<List<RequestItemDto>> getRequests() {
        return requests;
    }

    // Exponer el item seleccionado (detalle)
    public LiveData<RequestItemDto> getSelectedRequest() {
        return Transformations.map(requests, list -> {
            String id = selectedRequestId.getValue();
            if (id == null || list == null) return null;
            for (RequestItemDto item : list) {
                if (id.equals(item.getRequestId())) return item;
            }
            return null;
        });
    }

    // Seleccionar un request para detalle
    public void selectRequest(String requestId) {
        selectedRequestId.setValue(requestId);
    }

    // Agregar una nueva notificación en estado inicial (Procesando)
    public void addRequest(RequestItemDto item) {
        List<RequestItemDto> current = new ArrayList<>(requests.getValue());
        current.add(item);
        requests.setValue(current);
    }

    // Actualizar estado y fruta cuando llega el resultado por WS (Listo o Error)
    public void updateRequest(String requestId, String status, FruitDto fruit) {
        List<RequestItemDto> current = new ArrayList<>(requests.getValue());
        for (RequestItemDto item : current) {
            if (item.getRequestId().equals(requestId)) {
                item.setStatus(status);
                item.setFruit(fruit);
                break;
            }
        }
        requests.setValue(current);
    }

    public int getRequestsCount() {
        List<RequestItemDto> list = requests.getValue();
        return list != null ? list.size() : 0;
    }
}
