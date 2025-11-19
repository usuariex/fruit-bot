import { conexionbd } from "../db.js";

//Funcion para devolver las frutas más vistas
export const getTopFrutas = async (req, res) => {
    try {
        const [rows] = await conexionbd.query(`
        SELECT 
            f.NOMBRE as nombre,
            COUNT (l.FRUTA_ID) as vistas
        FROM log l
        JOIN fruta f ON l.FRUTA_ID = f.FRUTA_ID
        WHERE l.TIPO_LOG_ID = 3
        GROUP BY l.FRUTA_ID
        ORDER BY vistas DESC
        LIMIT 10; -- Por ahora muestro solo 10    
        `);
        res.status(200).json(rows);
    } catch (error) {
        console.log("Error la obtener las frutas más vistas", error);
        res.status(500).json({error: "Error interno del servidor"});
    }
};

// Funcion para devolver los departamentos con más vistas
export const getTopDepartamentos = async (req, res) => {
  try {
    const [rows] = await conexionbd.query(`
      SELECT 
        d.NOMBRE as nombre, 
        COUNT(l.DEPARTAMENTO_ID) as visitas
      FROM log l
      JOIN departamento d ON l.DEPARTAMENTO_ID = d.DEPT_ID
      WHERE l.TIPO_LOG_ID = 4 -- IMPORTANTE: Asumiendo que 4 = 'ver_departamento'
      GROUP BY l.DEPARTAMENTO_ID
      ORDER BY visitas DESC
      LIMIT 5; -- Traemos el top 5
    `);
    res.status(200).json(rows);
  } catch (error) {
    console.error("Error al obtener top departamentos:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};

// Devolver el número de sesiones iniciadas por día.
export const getActividadSesiones = async (req, res) => {
  try {
    const [rows] = await conexionbd.query(`
      SELECT 
        DATE(FECHA) as dia, 
        COUNT(SESSION_ID) as sesiones
      FROM session
      GROUP BY dia
      ORDER BY dia ASC;
    `);
    res.status(200).json(rows);
  } catch (error) {
    console.error("Error al obtener actividad de sesiones:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};