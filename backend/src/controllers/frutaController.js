import { conexionbd } from "../db.js";
import multer from 'multer';
import path from 'path';
import { fileURLToPath } from 'url';
import { dirname } from 'path';


const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);




const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, path.join(__dirname, "../../public/images_android/temp"));
  },
  filename: (req, file, cb) => {
    /* const uniqueName = Date.now() + "-" + file.originalname;
    cb(null, uniqueName); */

     cb(null, file.originalname);
  },
});

const upload = multer({ storage });

 /* subir fruta */
export const uploadFruit = [
  upload.single("file"), 
  async (req, res) => {
    try {
      const text = req.body.text;
      const file = req.file;

      if (!file) {
        return res.status(400).json({ error: "No file uploaded" });
      }

      res.json({
        message: "Upload successful",
        textReceived: text,
        filePath: `/public/images_android/temp/${file.filename}`,
      });
    } catch (err) {
      res.status(500).json({ error: "Server error", details: err.message });
    }
  },
];




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
