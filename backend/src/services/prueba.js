import { detectarFrutaConIA } from "./api_detector_ia.js";
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const ruta = path.resolve(__dirname, '../../public/images_android/temp/capture.jpg');

async function miFunction() {
    try {
        console.log("Iniciando detección...");
        const resultado = await detectarFrutaConIA(ruta, "fruta tropical");
        console.log("Resultado:");
        console.log(resultado);
    } catch (error) {
        console.error("Error:", error);
    }
}

miFunction();