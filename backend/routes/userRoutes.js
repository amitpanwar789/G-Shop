import express from "express";
import {
  authUser,
  getUserProfile,
  registerUser,
  updateUserProfile,
  getUsers,
  deleteUser,
  getUserById,
  updateUser,
} from "../controller/userControllers.js";
import { checkAuth, checkAdmin } from "../middleware/authMiddleware.js";
const router = express.Router();

router.route("/").post(registerUser).get(checkAuth, checkAdmin, getUsers);

router.route("/login").post(authUser);

router
  .route("/profile")
  .get(checkAuth, getUserProfile)
  .put(checkAuth, updateUserProfile);
router
  .route("/:id")
  .delete(checkAuth, checkAdmin, deleteUser)
  .get(checkAuth, checkAdmin, getUserById)
  .put(checkAuth, checkAdmin, updateUser);

export default router;
