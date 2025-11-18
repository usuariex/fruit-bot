package com.devs.frutybot.presentation.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.ws.WsManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestDetailViewModel extends ViewModel {
    private final MutableLiveData<String> resultLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final WsManager wsManager;

    public RequestDetailViewModel(WsManager wsManager) {
        this.wsManager = wsManager;
    }

    public LiveData<String> getResultLiveData() {
        return resultLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void subscribeToResult(String requestId) {
        wsManager.subscribe(requestId, new WsManager.WsCallback() {

            @Override
            public void onResult(String requestId, String json) {
                try {
                    JSONObject obj = new JSONObject(json);
                    JSONObject result = obj.getJSONObject("result");

                    String fruitName = result.optString("fruitName");
                    String status = result.optString("status");

                    resultLiveData.postValue("Fruta: " + fruitName + "\nEstado: " + status);
                } catch (JSONException e) {
                    errorLiveData.postValue("Error parseando respuesta: " + e.getMessage());
                }
            }



            @Override
            public void onError(String requestId, String error) {
                errorLiveData.postValue(error);
            }
        });
    }

}



