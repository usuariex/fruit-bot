import OpenAI from "openai";
import dotenv from "dotenv";
import { z } from "zod";
import { zodTextFormat } from "openai/helpers/zod";
import fs from "fs";

dotenv.config();

const apiKey = process.env.API_KEY;
const modelo = process.env.MODEL;
const client = new OpenAI({ apiKey });

// Esquema con Zod
const ModeloDelJSON = z.object({
  nombre: z.string(),
  pais: z.string(),
  departamento: z.string(),
  descripcion: z.string(),
  proceso_de_maduracion: z.string(),
  informacion_nutricional: z.string(),
  calorias: z.string(),
  vitaminas: z.array(z.string()),
  fibra: z.string(),
  azucares: z.string(),
  temporada: z.string(),
  tipo: z.string(),
  valida: z.string(),
});

function img_to_base64(imagenPath) {
  const contenido_img = fs.readFileSync(imagenPath);
  return contenido_img.toString("base64");
}

export const detectarFrutaConIA = async (imagePath, pista) => {
  const pregunta = "¿Que fruta es la que se muestra en la imagen?";
  const imagen_convertida = img_to_base64(imagePath);

  const lista_user_dinamica = [
    { type: "input_text", text: pregunta }
  ];

  if (pista && pista.trim().length > 0) {
    lista_user_dinamica.push({ type: "input_text", text: `Pista: ${pista}` });
  }

  lista_user_dinamica.push({
    type: "input_image",
    image_url: `data:image/jpeg;base64,${imagen_convertida}`,
    detail: "high",
  });

  const response = await client.responses.parse({
    model: modelo,
    input: [
      {
        role: "developer",
        content: "Debes tomar como prioridad las frutas que se producen en el perú como primera opcion a respuesta, despues de verificar puedes ver si es originaria de otra parte del mundo. Debes mantener EXACTAMENTE las siguientes instrucciones para tus respuestas: nombre(nombre de la fruta, deberás ser especifico con el nombre tal como:  manzana israel, etc. No nombres cientificos. No tildes), pais(Colocaras el pais de origen de la fruta), proceso_de_maduracion(describe cómo madura la fruta), departamento(deberás darme como prioridad unicamente un departamento del perú en el que es más producida y de el cual es originaria tal como la libertad, loreto, amazonas, ancash, ica, etc.), temporada(meses de cosecha), tipo(tipo de fruta: tropical, cítrica, etc.), valida(Puede ser True o False, representa si la imagen cumple con estandares de UX para publicarse). Reglas adicionales:\n1. Respeta el orden de las claves exactamente como se muestra. Además si el usuario despues de mandar la pregunta te envia un nombre de algo, tomalo como una posible sugerencia de lo que podria ser lo que se encuentra en la imagen."
      },
      {
        role: "user",
        content: lista_user_dinamica
      }
    ],
    text: {
      format: zodTextFormat(ModeloDelJSON, "datos_fruta"),
    },
  });

  return response.output_parsed;
};