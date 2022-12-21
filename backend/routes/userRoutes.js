import express from "express";
import { authUser, getUserProfile, registerUser } from "../controller/userControllers.js";
import { checkAuth } from "../middleware/authMiddleware.js";
const router = express.Router();


router.route('/').post(registerUser)

router.route('/login').post(authUser);

router.route('/profile').get(checkAuth, getUserProfile)

export default router;

