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
        WHERE l.TIPO_LOG_ID = 2
        GROUP BY l.FRUTA_ID
        ORDER BY vistas DESC
        LIMIT 10; -- Por ahora muestro solo 10    
        `);
        res.status(200).json(rows);
    } catch (error) {
        console.error("Error al obtener las frutas más vistas:", error);
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
      WHERE l.TIPO_LOG_ID = 3 
      GROUP BY l.DEPARTAMENTO_ID
      ORDER BY visitas DESC
      LIMIT 5; -- Traemos el top 5
    `);
    res.status(200).json(rows);
  } catch (error) {
    console.error("Error al obtener los departamentos más visitados:", error);
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
    console.error("Error al obtener la actividad de sesiones:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};

// Devuelve la distribución de los tipos de log.
export const getLogDistribution = async (req, res) => {
  try {
    const [rows] = await conexionbd.query(`
      SELECT 
        tl.NOMBRE as nombre, 
        COUNT(l.LOG_ID) as total
      FROM log l
      JOIN tipo_log tl ON l.TIPO_LOG_ID = tl.ID_TIPO_LOG
      GROUP BY l.TIPO_LOG_ID
      ORDER BY total DESC;
    `);
    // Renombramos 'total' a 'vistas' para que el frontend lo procese sin cambios.
    const formattedRows = rows.map(row => ({ nombre: row.nombre, vistas: row.total }));
    res.status(200).json(formattedRows);
  } catch (error) {
    console.error("Error al obtener la distribución de logs:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};

// Devuelve el consumo de tokens por día del último mes.
export const getTokenUsageByDay = async (req, res) => {
  try {
    const [rows] = await conexionbd.query(`
      SELECT 
        DATE(created_at) as dia, 
        SUM(input) as total_input,
        SUM(output) as total_output
      FROM tokens
      -- WHERE YEAR(created_at) = YEAR(NOW()) AND MONTH(created_at) = MONTH(NOW()) -- Comentamos el filtro para depurar
      GROUP BY dia
      ORDER BY dia ASC;
    `);
    // Ya no es necesario formatear, enviamos los datos directamente.
    res.status(200).json(rows);
  } catch (error) {
    console.error("Error al obtener el consumo de tokens:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};

// Devuelve el resumen total de tokens (input/output) del mes actual.
export const getCurrentMonthTokenSummary = async (req, res) => {
  try {
    const [rows] = await conexionbd.query(`
      SELECT 
        SUM(input) as total_input,
        SUM(output) as total_output
      FROM tokens
      -- WHERE YEAR(created_at) = YEAR(NOW()) AND MONTH(created_at) = MONTH(NOW()); -- Comentamos el filtro para depurar
    `);
    // Devolvemos el primer (y único) objeto del array, o ceros si no hay datos.
    res.status(200).json(rows[0] || { total_input: 0, total_output: 0 });
  } catch (error) {
    console.error("Error al obtener el resumen de tokens:", error);
    res.status(500).json({ error: "Error interno del servidor" });
  }
};