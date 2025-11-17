import { WebSocketServer } from "ws";

// Diccionario para guardar las conexiones activas
const clients = {};

// Crear el servidor WebSocket
const wss = new WebSocketServer({ port: 8080 });

wss.on("connection", (ws) => {
  console.log("Cliente conectado");

  ws.on("message", (msg) => {
    try {
      const data = JSON.parse(msg);
      if (data.type === "subscribe" && data.requestId) {
        // Asociar el socket con el requestId
        clients[data.requestId] = ws;
        console.log(`Cliente suscrito con requestId: ${data.requestId}`);
      }
    } catch (err) {
      console.error("Error al procesar mensaje WS:", err.message);
    }
  });
});

// Función para notificar resultados a un cliente específico
export function notifyResult(requestId, result) {
  const ws = clients[requestId];
  if (ws && ws.readyState === 1) {
    ws.send(JSON.stringify({ status: "done", result }));
    delete clients[requestId]; // limpiar después de enviar
  }
}
