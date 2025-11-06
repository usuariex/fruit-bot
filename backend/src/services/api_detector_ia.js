const { OpenAI } = require('openai');
const dotenv = require('dotenv');
const fs = require('fs');
const path = require('path');

dotenv.config();

const apiKey = process.env.API_KEY;
const modelo = process.env.MODEL;

const client = new OpenAI({ apiKey: apiKey });

const pregunta = "¿Que fruta es la que se muestra en la imagen?";

const imagen = path.join(__dirname, '..', '..', 'public', 'images', 'lucuma2.jpg');
const pista = "lucm";


async function detectarFruta() {
    const imagen_convertida = fs.readFileSync(imagen, "base64");

    const lista_user_dinamica = [
        {
            "type": "input_text",
            "text": pregunta
        }
    ];

    if (pista && pista.trim()) {
        lista_user_dinamica.push({ "type": "input_text", "text": pista });
    }

    lista_user_dinamica.push({
        "type": "input_image",
        "image_url": `data:image/jpg;base64,${imagen_convertida}`,
        "detail": "high"
    });

    const response = await client.responses.create({
        model: modelo,
        input: [
            {
                "role": "developer",
                "content": "Responde SIEMPRE en formato JSON, sin texto adicional. Debes tomar como prioridad las frutas que se producen en el perú como primera opcion a respuesta, despues de verificar que no sea una fruta originaria del peru puedes ver si es originaria de otra parte del mundo. Debes mantener EXACTAMENTE el siguiente orden de claves y estructura:\n\n{\n  \"nombre\": \"(nombre de la fruta, deberás ser especifico con el nombre tal como: palta hass, manzana israel, manzana pachacamac, etc. No nombres cientificos. No tildes)\",\n  \"departamento\": \"(aquí deberás darme como prioridad unicamente un departamento del perú en el que es más producida y del cual es originaria la fruta tal como la libertad, loreto, amazonas, ancash, ica, etc. Y si la fruta no es originaria del peru, colocarás su departamento del que es originario y el pais de origen en ese orden)\",\n  \"descripcion\": {\n    \"proceso_de_maduracion\": \"(describe cómo madura la fruta)\",\n    \"informacion_nutricional\": {\n      \"calorias\": (número entero o decimal),\n      \"vitamina_c_mg\": (número decimal en mg),\n      \"fibra_g\": (número decimal en g),\n      \"azucares_g\": (número decimal en g)\n    },\n    \"temporada\": \"(meses de cosecha)\",\n    \"tipo\": \"(tipo de fruta: tropical, cítrica, etc.)\"\n  },\n  \"valida\": true\n}\n\nReglas adicionales:\n1. No incluyas texto fuera del JSON.\n2. Usa siempre comillas dobles para las claves y valores de texto.\n3. Respeta el orden de las claves exactamente como se muestra.\n4. 'valida' debe ser un valor booleano (true o false) que indique si la fruta cumple estándares para publicarse en una app. Además si el usuario despues de mandar la pregunta te envia un nombre de algo, tomalo como una posible sugerencia de lo que podria ser lo que se encuentra en la imagen."
            },
            {
                "role": "user",
                "content": lista_user_dinamica,
            },
        ]
    });

    const respuesta_api = response.output_text;
    const informacion_json = JSON.parse(respuesta_api);

    console.log(informacion_json);
    console.log(informacion_json["departamento"]);
}

detectarFruta();