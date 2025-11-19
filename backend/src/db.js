import mysql from 'mysql2';
import { config } from './config.js';


const con = mysql.createConnection({
  host: config.dbHost,
  port: config.dbPort,
  user: config.dbUser,
  password: config.dbPass,
  database: config.dbName,
});


export const conexionbd = con.promise();
