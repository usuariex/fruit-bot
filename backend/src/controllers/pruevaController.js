import { conexionbd } from '../db.js'; 

export const estado_conexion = async(req, res) =>{
    res.json({"conexion": "conexion exitosa"})
}