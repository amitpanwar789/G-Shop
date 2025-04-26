import mongoose from "mongoose";

const connectDB = async()=>{
    try{
        console.log("Connecting to database...", process.env.MONGO_URI);
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