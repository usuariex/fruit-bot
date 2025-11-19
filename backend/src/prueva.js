import { detectarFrutaConIA } from "./services/api_detector_ia copy.js";

const ruta = "/home/elu/v/fruit-bot/backend/public/images_android/temp/uvas.jpeg";

function  miFunction() {
     detectarFrutaConIA(ruta, "Es una fruta tropical");
}


miFunction();