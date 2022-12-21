import express  from 'express'
import dotenv from 'dotenv'
import connectDB from './config/db.js'
import productRoutes from './routes/productRoutes.js'
import { notFound,errorHandler } from './middleware/errorMiddleware.js'
import userRoutes from './routes/userRoutes.js'

const app = express();
dotenv.config()
const PORT = process.env.PORT||5000;
connectDB();

app.use(express.json());
app.use('/api/products',productRoutes)
app.use('/api/users',userRoutes)

app.use(notFound)
app.use(errorHandler)


app.listen(PORT,()=>{
    console.log("Server is runnning");
})

