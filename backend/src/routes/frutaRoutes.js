import { Router } from 'express';
import {
    listarFrutas,
    listarFrutasPorDepto,
    recibirImg,
    buscarFrutas                
} from '../controllers/frutaController.js';
import multer from 'multer';
import path from 'path';




const router = Router();



const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, path.join(process.cwd(), "public/images_android/temp"));
  },
  filename: (req, file, cb) => {
    cb(null, file.originalname);
  },
});


const upload = multer({ storage });


router.get('/', listarFrutas)
router.get('/depto', listarFrutasPorDepto)
router.post("/recibirImg", upload.single("file"), recibirImg);
router.get("/buscar", buscarFrutas);



export default router;