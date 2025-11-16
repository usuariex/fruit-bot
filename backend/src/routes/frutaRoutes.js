import { Router } from 'express';
import {
    listarFrutas,
    listarFrutasPorDepto,
    uploadFruit

} from '../controllers/frutaController.js';


const router = Router();

router.get('/', listarFrutas)
router.get('/depto', listarFrutasPorDepto)
router.post("/subirImg", uploadFruit);


export default router;