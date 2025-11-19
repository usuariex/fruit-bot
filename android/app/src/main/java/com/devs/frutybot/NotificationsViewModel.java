package com.devs.frutybot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.dto.RequestItemDto;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<List<RequestItemDto>> requests = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> selectedRequestId = new MutableLiveData<>();

    // MediatorLiveData que emite el RequestItemDto correcto cuando cambian requests o selectedRequestId
    private final MediatorLiveData<RequestItemDto> selectedRequest = new MediatorLiveData<>();

    public NotificationsViewModel() {
        selectedRequest.addSource(requests, list -> updateSelectedRequest());
        selectedRequest.addSource(selectedRequestId, id -> updateSelectedRequest());
    }

    private void updateSelectedRequest() {
        String id = selectedRequestId.getValue();
        List<RequestItemDto> list = requests.getValue();
        if (id == null || list == null) {
            selectedRequest.setValue(null);
            return;
        }
        for (RequestItemDto item : list) {
            if (id.equals(item.getRequestId())) {
                selectedRequest.setValue(item);
                return;
            }
        }
        selectedRequest.setValue(null);
    }

    // Exponer la lista completa para renderizar las notificaciones
    public LiveData<List<RequestItemDto>> getRequests() {
        return requests;
    }

    // Exponer el item seleccionado (detalle)
    public LiveData<RequestItemDto> getSelectedRequest() {
        return selectedRequest;
    }

    // Seleccionar un request para detalle
    public void selectRequest(String requestId) {
        selectedRequestId.setValue(requestId);
    }

    // Agregar una nueva notificación en estado inicial (Procesando)
    public void addRequest(RequestItemDto item) {
        List<RequestItemDto> current = new ArrayList<>();
        if (requests.getValue() != null) current.addAll(requests.getValue());
        current.add(item);
        requests.setValue(current);
    }

    // Actualizar estado y fruta cuando llega el resultado por WS (Listo o Error)
    public void updateRequest(String requestId, String status, FruitDto fruit) {
        List<RequestItemDto> current = new ArrayList<>();
        if (requests.getValue() != null) current.addAll(requests.getValue());
        boolean changed = false;
        for (RequestItemDto item : current) {
            if (item.getRequestId().equals(requestId)) {
                item.setStatus(status);
                item.setFruit(fruit);
                changed = true;
                break;
            }
        }
        if (changed) {
            requests.setValue(current); // disparará observers y recalculará selectedRequest
        }
    }

    public int getRequestsCount() {
        List<RequestItemDto> list = requests.getValue();
        return list != null ? list.size() : 0;
    }
}
