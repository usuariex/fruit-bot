package com.devs.frutybot.data.ws;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.*;

public class WsManager {
    private OkHttpClient client;
    private WebSocket webSocket;

    private final Map<String, WsCallback> callbacks = new HashMap<>();

    public interface WsCallback {
        void onResult(String requestId, String json);
        void onError(String requestId, String error);
    }

    public void connect(String url) {

        if (webSocket != null) {
            disconnect();
        }

        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                try {
                    JSONObject obj = new JSONObject(text);
                    String requestId = obj.optString("requestId", "");
                    WsCallback cb = callbacks.get(requestId);
                    if (cb != null) {
                        cb.onResult(requestId, text);
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {

            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                for (Map.Entry<String, WsCallback> entry : callbacks.entrySet()) {
                    try {
                        entry.getValue().onError(entry.getKey(), t == null ? "unknown" : t.getMessage());
                    } catch (Exception ignored) {}
                }
            }
        });


    }

    public void subscribe(String requestId, WsCallback callback) {
        if (requestId == null || callback == null) return;
        callbacks.put(requestId, callback);
        if (webSocket != null) {
            String msg = "{\"type\":\"subscribe\",\"requestId\":\"" + requestId + "\"}";
            webSocket.send(msg);
        }
    }

    public void unsubscribe(String requestId) {
        if (requestId == null) return;
        callbacks.remove(requestId);
        if (webSocket != null) {
            String msg = "{\"type\":\"unsubscribe\",\"requestId\":\"" + requestId + "\"}";
            webSocket.send(msg);
        }
    }

    public boolean isConnected() {
        return webSocket != null;
    }

    public void sendRaw(String json) {
        if (webSocket != null && json != null) {
            webSocket.send(json);
        }
    }


    public void disconnect() {
        try {
            if (webSocket != null) {
                webSocket.close(1000, "Client disconnect");
                webSocket = null;
            }
        } catch (Exception ignored) {}

        try {
            if (client != null) {

                client.dispatcher().executorService().shutdown();
                client.connectionPool().evictAll();

                client = null;
            }
        } catch (Exception ignored) {}


        callbacks.clear();
    }
}
