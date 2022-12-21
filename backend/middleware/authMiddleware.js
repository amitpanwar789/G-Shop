import AsyncHandler from "express-async-handler";
import jwt from "jsonwebtoken";
import User from "../models/userModel.js";

const checkAuth = AsyncHandler(async (req, res, next) => {
  const headerAuth = req.headers.authorization;
  if (headerAuth && headerAuth.startsWith("Bearer")) {
    try {
      const token = headerAuth.split(" ")[1];
      const decoded = jwt.verify(token, process.env.JWT_SECRET);
      req.user = await User.findById(decoded.id).select("-password");
      next();
    } catch (error) {
      res.status(401);
      throw new Error("Not authorized, token failed");
    }
  }
});

export { checkAuth };
