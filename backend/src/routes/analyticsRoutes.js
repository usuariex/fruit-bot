import { Router } from 'express';
import { 
    getTopFrutas, 
    getTopDepartamentos, 
    getActividadSesiones 
} from '../controllers/analyticsController.js';

const router = Router();

// Ruta para obtener las frutas más populares
router.get('/top-frutas', getTopFrutas);

// Ruta para obtener los departamentos más visitados
router.get('/top-departamentos', getTopDepartamentos);

// Ruta para obtener la actividad de sesiones por día
router.get('/actividad-sesiones', getActividadSesiones);

export default router;
