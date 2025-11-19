import { conexionbd } from "../db.js";


export async function guardarResultBD(resultado) {
  try {
    await conexionbd.query(
      "INSERT INTO tokens (id, input, output, total, created_at) VALUES (?, ?, ?, ?, NOW())",
      [
        resultado.id,
        resultado.tokens.input,
        resultado.tokens.output,
        resultado.tokens.total
      ]
    );
    console.log("Resultado guardado en BD:", resultado.id);
  } catch (error) {
    console.error("Error guardando en BD:", error.message);
  }
}
