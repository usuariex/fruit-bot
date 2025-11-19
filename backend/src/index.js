import express from "express";
import cors from "cors";
import { WebSocketServer } from "ws";
import path from "path";
import { fileURLToPath } from "url";
import frutaRoutes from "./routes/frutaRoutes.js";
import pruevaServer from "./routes/pruevaServer.js";
import { config } from "./config.js";


const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);


const app = express();
app.use(cors());
app.use(express.json());

app.use('/', pruevaServer);
app.use('/frutas', frutaRoutes);


app.use("/public/depto", express.static(path.join(__dirname, "../public/images_android/depto")));
app.use("/public/fruta", express.static(path.join(__dirname, "../public/images_android/Fruta")));
app.use("/public/temp", express.static(path.join(__dirname, "../public/images_android/temp")));


const port = config.port;


const server = app.listen(port, "0.0.0.0", () => {
  console.log(`Servidor en http://localhost:${port}`);
});


const clients = {};
const ws_server = new WebSocketServer({ server, path: "/ws" });


ws_server.on("connection", (ws) => {
  console.log("Cliente WS conectado");

  ws.on("message", (msg) => {
    try {
      const data = JSON.parse(msg);
      if (data.type === "subscribe" && data.requestId) {
        clients[data.requestId] = ws;
        console.log(`Cliente suscrito con requestId: ${data.requestId}`);
      }
    } catch (err) {
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