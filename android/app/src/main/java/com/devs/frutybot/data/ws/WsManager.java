package com.devs.frutybot.data.ws;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.*;

public class WsManager {
    private OkHttpClient client;
    private WebSocket webSocket;

    // Aquí guardamos los callbacks asociados a cada requestId
    private final Map<String, WsCallback> callbacks = new HashMap<>();

    public interface WsCallback {
        void onResult(String requestId, String json);
        void onError(String requestId, String error);
    }

    public void connect(String url) {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                try {
                    JSONObject obj = new JSONObject(text);
                    String requestId = obj.optString("requestId");
                    WsCallback cb = callbacks.get(requestId);
                    if (cb != null) {
                        cb.onResult(requestId, text);
                    }
                } catch (JSONException e) {
                    // manejar error de parseo
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                for (Map.Entry<String, WsCallback> entry : callbacks.entrySet()) {
                    entry.getValue().onError(entry.getKey(), t.getMessage());
                }
            }
        });
    }

    // Ahora sí acepta el callback
    public void subscribe(String requestId, WsCallback callback) {
        callbacks.put(requestId, callback);
        if (webSocket != null) {
            String msg = "{\"type\":\"subscribe\",\"requestId\":\"" + requestId + "\"}";
            webSocket.send(msg);
        }
    }
}
