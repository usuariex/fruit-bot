import { Router } from 'express';
import { estado_conexion} from '../controllers/pruevaController.js';


const router = Router();

router.get('/',estado_conexion)



export default router;