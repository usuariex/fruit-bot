package com.devs.frutybot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.dto.RequestItemDto;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<List<RequestItemDto>> requests = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<RequestItemDto>> getRequests() {
        return requests;
    }

    public void addRequest(RequestItemDto item) {
        List<RequestItemDto> current = new ArrayList<>(requests.getValue());
        current.add(item);
        requests.setValue(current);
    }

    public void updateRequest(String requestId, String status, FruitDto fruit)
    {
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
        return requests.getValue().size();
    }
}
