import express from "express";
const router = express.Router();
import {
  addOrderItems,
  getMyOrders,
  getOrderById,
  updateOrderToDelivered,
  updateOrderToPaid,
} from "../controllers/orderController.js";
import { checkAdmin, checkAuth } from "../middleware/authMiddleware.js";

router.route("/").post(checkAuth, addOrderItems).get(checkAuth, checkAdmin,getMyOrders);
router.route('/myorders').get(checkAuth, getMyOrders)
router.route("/:id").get(checkAuth, getOrderById);
router.route("/:id/pay").put(checkAuth, updateOrderToPaid);
router.route('/:id/deliver').put(checkAuth, checkAdmin, updateOrderToDelivered)

export default router;
