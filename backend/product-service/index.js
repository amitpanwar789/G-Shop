import path from "path";
import express from "express";
import morgan from "morgan";
import dotenv from "dotenv";
import connectDB from "./config/db.js";
import productRoutes from "./routes/productRoutes.js";
import uploadRoutes from "./routes/uploadRoutes.js";
import { notFound, errorHandler } from "./middleware/errorMiddleware.js";

import cors from 'cors';




const app = express();
dotenv.config();
const PORT = process.env.PORT || 3000;
connectDB();

app.use(cors({
  origin: '*',
  credentials: true,
}));


app.use(morgan("dev"));


app.use(express.json());
app.use("/api/products", productRoutes);
app.use("/api/upload", uploadRoutes);

const __dirname = path.resolve();
app.use("/uploads", express.static(path.join(__dirname, "/uploads")));


app.use(notFound);
app.use(errorHandler);

app.listen(PORT, () => {
  console.log("Server is runnning");
});
