import path from "path";
import express from "express";
import morgan from "morgan";
import dotenv from "dotenv";
import connectDB from "./config/db.js";
import { notFound, errorHandler } from "./middleware/errorMiddleware.js";
import orderRoutes from "./routes/orderRoutes.js";
import cors from 'cors';




const app = express();
dotenv.config();
const PORT = process.env.PORT || 3000;
connectDB();

app.use(cors({
  origin: '*',
  credentials: true,
}));

if (process.env.NODE_ENV === "development") {
  app.use(morgan("dev"));
}

app.use(express.json());
app.use("/api/orders", orderRoutes);


app.use(notFound);
app.use(errorHandler);

app.listen(PORT, () => {
  console.log("Server is runnning");
});
