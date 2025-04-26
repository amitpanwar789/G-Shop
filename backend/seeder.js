import mongoose from 'mongoose'
import dotenv from 'dotenv'
import users from './data/users.js'
import products from './data/products.js'
import User from './user-service/modals/userModel.js'
import Product from './product-service/models/productModel.js'
import Order from './order-service/models/orderModel.js'

dotenv.config()

try {
  const connect = await mongoose.connect("mongodb://localhost:27018/products-service", {
    useUnifiedTopology: true,
    useNewUrlParser: true,
  })
  console.log("Database connected")
}
catch (err) {
  console.error(err);
}

const importData = async () => {
  try {
    // await Order.deleteMany()
    await Product.deleteMany()
    // await User.deleteMany()

    // const createdUsers = await User.insertMany(users)

    const adminUser = "67f8cab7021668485d0a5644"

    const sampleProducts = products.map((product) => {
      return { ...product, user: adminUser }
    })

    await Product.insertMany(sampleProducts)

    console.log('Data Imported!')
    process.exit()
  } catch (error) {
    console.error(`${error}`)
    process.exit(1)
  }
}

const destroyData = async () => {
  try {
    await Order.deleteMany()
    await Product.deleteMany()
    await User.deleteMany()

    console.log('Data Destroyed!')
    process.exit()
  } catch (error) {
    console.error(`${error}`)
    process.exit(1)
  }
}

if (process.argv[2] === '-d') {
  destroyData()
} else {
  importData()
}