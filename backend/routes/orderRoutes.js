import express from "express";
const router = express.Router();
import {
  addOrderItems,
  getMyOrders,
  getOrderById,
  updateOrderToPaid,
} from "../controller/orderController.js";
import { checkAuth } from "../middleware/authMiddleware.js";

router.route("/").post(checkAuth, addOrderItems);
router.route('/myorders').get(checkAuth, getMyOrders)
router.route("/:id").get(checkAuth, getOrderById);
router.route("/:id/pay").put(checkAuth, updateOrderToPaid);

export default router;
