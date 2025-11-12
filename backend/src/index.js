const express = require('express');
const app = express();
const port = process.env.PORT || 3000;

app.get('/', (req, res) => {
  res.send('¡Hola desde el backend organizado!');
});

app.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);
});