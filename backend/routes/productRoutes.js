import express from "express";
import {
  createProduct,
  deleteProduct,
  getProductById,
  getProducts,
  updateProduct,
} from "../controller/productController.js";
const router = express.Router();
import { checkAuth, checkAdmin } from "../middleware/authMiddleware.js";

router.route("/").get(getProducts).post(checkAuth, checkAdmin, createProduct);
router
  .route("/:id")
  .get(getProductById)
  .delete(checkAuth, checkAdmin, deleteProduct)
  .put(checkAuth, checkAdmin, updateProduct);
export default router;
