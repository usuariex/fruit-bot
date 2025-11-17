package com.devs.frutybot.presentation.notifications;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devs.frutybot.data.ws.WsManager;

public class RequestDetailViewModelFactory implements ViewModelProvider.Factory {
    private final WsManager wsManager;

    public RequestDetailViewModelFactory(WsManager wsManager) {
        this.wsManager = wsManager;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RequestDetailViewModel.class)) {
            return (T) new RequestDetailViewModel(wsManager);
        }
        throw new IllegalArgumentException("vista deconocita");
    }
}
