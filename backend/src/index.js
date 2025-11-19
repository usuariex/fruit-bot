import express, { json } from 'express';
import cors from 'cors';
import path from 'path';
import { fileURLToPath } from 'url';

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

// Rutas de la API (unificadas bajo /api)
app.use('/api/test', pruevaServer); 
app.use('/api/frutas', frutaRoutes);
app.use('/api/analytics', analyticsRoutes); 

const port = 3020;

app.listen(port, "0.0.0.0", () => {
  console.log(`Servidor backend corriendo en http://localhost:${port}`);
});
