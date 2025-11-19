import { conexionbd } from "../db.js";
import { v4 as uuidv4 } from "uuid";
import { detectarFrutaConIA } from "../services/api_detector_ia.js";
import { config } from "../config.js";
import { guardarResultBD } from "../repository/frutaRepo.js"; 
import { notifyResult } from "../index.js";


export const recibirImg = async (req, res) => {
  try {
    const { text } = req.body;
    const file = req.file;

    if (!file) {
      return res.status(400).json({ error: "Imagen no subida" });
    }

    const requestId = uuidv4();
    console.log(`Imagen con requestId: ${requestId}, procesando...`);


    detectarFrutaConIA(file.path, text)

      .then(resultado => {

        console.log(requestId, {
          id: resultado.id,
          status: "done",
          fruit: resultado.fruit
        });



        notifyResult(requestId, {
          id: resultado.id,
          status: "done",
          fruit: resultado.fruit
        });
        guardarResultBD(resultado);
      })


      .catch(err => {
        notifyResult(requestId, {
          status: "error",        
          error: err.message
        });
      });


    res.json({
      message: "imagen recibida, procesando",
      requestId,
      status: "procesando",
      imageUrl: config.baseUrlTemp + file.filename
    });

  } catch (err) {

    res.status(500).json({ error: "Error del serividor", details: err.message });
  }
};




export const listarFrutas = async (req, res) => {
  try {
    const [rows] = await conexionbd.query(`
      SELECT 
        f.FRUTA_ID as fruta_id,
        f.NOMBRE as nombre,
        f.URL_IMG as url_imagen,
        f.DESCRIPCION as descripcion,
        d.NOMBRE AS departamento_nombre,
        d.DESCRIPCION AS departamento_descripcion
      FROM fruta f
      INNER JOIN departamento d ON f.DEPT_ID = d.DEPT_ID
    `);

    res.status(200).json(rows);
  } catch (error) {
    console.error("Error al consultar las frutas:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};


export const listarFrutasPorDepto = async (req, res) => {
  try {
    const { depto } = req.query;

    if (!depto) {
      return res.status(400).json({ error: "Debe proporcionar el parámetro ?depto" });
    }

    const [rows] = await conexionbd.query(`
      SELECT 
        f.FRUTA_ID,
        f.NOMBRE,
        f.URL_IMG,
        f.DESCRIPCION,
        d.NOMBRE AS departamento_nombre,
        d.DESCRIPCION AS departamento_descripcion
      FROM fruta f
      INNER JOIN departamento d ON f.DEPT_ID = d.DEPT_ID
      WHERE d.NOMBRE = ?
    `, [depto]);

    res.status(200).json(rows);
  } catch (error) {
    console.error("Error al consultar las frutas por departamento:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};
export const buscarFrutas = async (req, res) => {
  try {
    const { nombre } = req.query;

    if (!nombre || nombre.trim() === "") {
      return res.json([]); // Si no envían texto, devolvemos vacío
    }

    const query = `
      SELECT 
        f.FRUTA_ID,
        f.NOMBRE,
        f.URL_IMG,
        f.DESCRIPCION,
        d.NOMBRE AS departamento_nombre,
        d.DESCRIPCION AS departamento_descripcion
      FROM fruta f
      INNER JOIN departamento d ON f.DEPT_ID = d.DEPT_ID
      WHERE f.NOMBRE LIKE ?
    `;

    const [rows] = await conexionbd.query(query, [`%${nombre}%`]);

    res.status(200).json(rows);
  } catch (error) {
    console.error("Error al buscar frutas:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};

