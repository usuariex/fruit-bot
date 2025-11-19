import { conexionbd } from "../db.js";

async function guardarResultBD(resultado) {
  try {
    await conexionbd.query(
      "INSERT INTO resultados_ia (id, status, model, schema, tokens, output_text, fruit, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())",
      [
        resultado.id,
        resultado.status,
        resultado.model,
        JSON.stringify(resultado.schema),
        JSON.stringify(resultado.tokens),
        resultado.output_text,
        JSON.stringify(resultado.fruit)
      ]
    );
    console.log("Resultado guardado en BD:", resultado.id);
  } catch (error) {
    console.error("Error guardando en BD:", error.message);
  }
}
