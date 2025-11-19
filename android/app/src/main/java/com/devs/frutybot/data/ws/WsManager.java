package com.devs.frutybot.data.ws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WsManager {
    private static final String TAG = "WsManager";
    private OkHttpClient client;
    private WebSocket webSocket;

    private final Map<String, WsCallback> callbacks = new HashMap<>();

    public interface WsCallback {
        void onResult(String requestId, String json);
        void onError(String requestId, String error);
    }

    public void connect(String url) {
        Log.d(TAG, "Intentando conectar a: " + url);

        if (webSocket != null) {
            // Si ya está conectado o intentando, lo cerramos para reiniciar
            disconnect();
        }

        if (client == null) {
            client = new OkHttpClient.Builder()
                    .readTimeout(0, TimeUnit.MILLISECONDS) // Mantener viva la conexión
                    .build();
        }

        Request request = new Request.Builder().url(url).build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.d(TAG, "Conexión WS abierta correctamente.");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d(TAG, "Mensaje recibido WS: " + text);
                try {
                    JSONObject obj = new JSONObject(text);
                    String requestId = obj.optString("requestId", "");
                    
                    if (!requestId.isEmpty()) {
                        WsCallback cb = callbacks.get(requestId);
                        if (cb != null) {
                            cb.onResult(requestId, text);
                        } else {
                            Log.w(TAG, "No se encontró callback para requestId: " + requestId);
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parseando JSON WS: " + e.getMessage());
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.d(TAG, "Cerrando conexión WS: " + reason);
                webSocket.close(code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.d(TAG, "Conexión WS cerrada. Código: " + code);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.e(TAG, "Fallo en conexión WS: " + t.getMessage());
                for (Map.Entry<String, WsCallback> entry : callbacks.entrySet()) {
                    try {
                        entry.getValue().onError(entry.getKey(), t == null ? "unknown error" : t.getMessage());
                    } catch (Exception ignored) {}
                }
            }
        });
    }

    public void subscribe(String requestId, WsCallback callback) {
        if (requestId == null || callback == null) return;
        
        Log.d(TAG, "Suscribiendo a requestId: " + requestId);
        callbacks.put(requestId, callback);
        
        if (webSocket != null) {
            String msg = "{\"type\":\"subscribe\",\"requestId\":\"" + requestId + "\"}";
            Log.d(TAG, "Enviando mensaje de suscripción: " + msg);
            webSocket.send(msg);
        } else {
            Log.e(TAG, "No se pudo enviar suscripción, WebSocket es nulo");
        }
    }

    public void unsubscribe(String requestId) {
        if (requestId == null) return;
        Log.d(TAG, "Desuscribiendo requestId: " + requestId);
        callbacks.remove(requestId);
        // Opcional: avisar al backend si este soportara 'unsubscribe'
    }

    public void disconnect() {
        Log.d(TAG, "Desconectando WS...");
        try {
            if (webSocket != null) {
                webSocket.close(1000, "Client disconnect");
                webSocket = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error cerrando WS: " + e.getMessage());
        }

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
