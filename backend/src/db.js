import mysql from 'mysql2';

const con = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'db_frutas',
});


export const conexionbd = con.promise();
