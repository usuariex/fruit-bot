import express, { json } from 'express';
import cors from 'cors';
import path from 'path';
import { fileURLToPath } from 'url';
import { WebSocketServer } from "ws";
import { config } from "./config.js";

import frutaRoutes from './routes/frutaRoutes.js';
import pruevaServer from './routes/pruevaServer.js';
import analyticsRoutes from './routes/analyticsRoutes.js';


const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
app.use(cors());
app.use(express.json());

// Servir archivos estáticos (imágenes)
app.use("/public/depto", express.static(path.join(__dirname, "../public/images_android/depto")));
app.use("/public/fruta", express.static(path.join(__dirname, "../public/images_android/Fruta")));
app.use("/public/temp", express.static(path.join(__dirname, "../public/images_android/temp")));

// Rutas de la API (unificadas bajo /api)
app.use('/api/test', pruevaServer); 
app.use('/api/frutas', frutaRoutes);
app.use('/api/analytics', analyticsRoutes); 

const port = config.port;


const server = app.listen(port, "0.0.0.0", () => {
  console.log(`Servidor en http://localhost:${port}`);
});


const clients = {};
const ws_server = new WebSocketServer({ server, path: "/ws" });


ws_server.on("connection", (ws) => {
  console.log("Cliente WS conectado");

  ws.on("message", (msg) => {
      if (data.type === "subscribe" && data.requestId) {
    try {
      const data = JSON.parse(msg);
        clients[data.requestId] = ws;
        console.log(`Cliente suscrito con requestId: ${data.requestId}`);
    } catch (err) {
      }
      console.error("Error al procesar mensaje WS:", err.message);
    }
});
  });


export function notifyResult(requestId, result) {
  const ws = clients[requestId];
  if (ws && ws.readyState === 1) {
    ws.send(JSON.stringify({ requestId, ...result  }));
    delete clients[requestId];
  }
}
