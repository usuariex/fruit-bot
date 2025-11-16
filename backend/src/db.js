import mysql from 'mysql2';

const con = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'mysql_mysql',
  database: 'db_frutas',
});


export const conexionbd = con.promise();
