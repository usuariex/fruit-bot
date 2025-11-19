package com.devs.frutybot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.dto.FruitDtoResponse;
import com.devs.frutybot.data.dto.RequestItemDto;
import com.devs.frutybot.data.ws.WsManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<List<RequestItemDto>> requests = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> selectedRequestId = new MutableLiveData<>();
    private final MediatorLiveData<RequestItemDto> selectedRequest = new MediatorLiveData<>();
    
    private final WsManager wsManager;
    private final Gson gson = new Gson();

    @Inject
    public NotificationsViewModel(WsManager wsManager) {
        this.wsManager = wsManager;
        
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

    public LiveData<List<RequestItemDto>> getRequests() {
        return requests;
    }

    public LiveData<RequestItemDto> getSelectedRequest() {
        return selectedRequest;
    }

    public void selectRequest(String requestId) {
        selectedRequestId.setValue(requestId);
    }

    public void addRequest(RequestItemDto item) {
        List<RequestItemDto> current = new ArrayList<>();
        if (requests.getValue() != null) current.addAll(requests.getValue());
        current.add(item);
        requests.setValue(current);
        
        // Iniciar escucha automática para este request
        listenForUpdates(item.getRequestId());
    }

    public void updateRequest(String requestId, String status, FruitDto fruit) {
        List<RequestItemDto> current = new ArrayList<>();
        if (requests.getValue() != null) current.addAll(requests.getValue());
        boolean changed = false;
        for (RequestItemDto item : current) {
            if (item.getRequestId().equals(requestId)) {
                item.setStatus(status);
                if (fruit != null) {
                    item.setFruit(fruit);
                }
                changed = true;
                break;
            }
        }
        if (changed) {
            requests.postValue(current); 
        }
    }
    
    private void listenForUpdates(String requestId) {
        wsManager.subscribe(requestId, new WsManager.WsCallback() {
            @Override
            public void onResult(String requestId, String json) {
                try {
                    // Parsear usando FruitDtoResponse que envuelve todo
                    FruitDtoResponse response = gson.fromJson(json, FruitDtoResponse.class);
                    String status = response.getStatus();

                    if ("done".equals(status)) {
                        updateRequest(requestId, "Listo", response.getFruit());
                        // Opcional: desuscribirse si ya terminó
                        // wsManager.unsubscribe(requestId); 
                    } else if ("error".equals(status)) {
                        updateRequest(requestId, "Error", null);
                    }
                } catch (Exception e) {
                    updateRequest(requestId, "Error", null);
                }
            }

            @Override
            public void onError(String requestId, String error) {
                updateRequest(requestId, "Error", null);
            }
        });
    }

    public int getRequestsCount() {
        List<RequestItemDto> list = requests.getValue();
        return list != null ? list.size() : 0;
    }
}
