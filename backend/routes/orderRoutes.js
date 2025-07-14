import express from "express";
const router = express.Router();
import {
  getOrders,
  addOrderItems,
  getMyOrders,
  getOrderById,
  updateOrderToDelivered,
  updateOrderToPaid,
} from "../controller/orderController.js";
import { checkAdmin, checkAuth } from "../middleware/authMiddleware.js";

router.route("/").post(checkAuth, addOrderItems).get(checkAuth, checkAdmin,getOrders);
router.route('/myorders').get(checkAuth, getMyOrders)
router.route("/:id").get(checkAuth, getOrderById);
router.route("/:id/pay").put(checkAuth, updateOrderToPaid);
router.route('/:id/deliver').put(checkAuth, checkAdmin, updateOrderToDelivered)

export default router;
