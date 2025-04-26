import mongoose from "mongoose";

const connectDB = async()=>{
    try{
        const connect = await mongoose.connect(process.env.MONGO_URI, {
            useUnifiedTopology:true,
            useNewUrlParser:true,
        })
        console.log("Database connected")
    }
    catch(err){
        console.error(err);
    }
}

export default connectDB;