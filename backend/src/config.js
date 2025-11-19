import dotenv from "dotenv";
dotenv.config();

export const config = {
  dbHost: process.env.DB_HOST,
  dbPort: process.env.DB_PORT,
  dbName: process.env.DB_NAME,
  dbUser: process.env.DB_USER,
  dbPass: process.env.DB_PASS,
  port: process.env.PORT,
  baseUrlTemp: `http://${process.env.MI_IPLOCAL}:${process.env.PORT}/public/images_android/temp/`
};