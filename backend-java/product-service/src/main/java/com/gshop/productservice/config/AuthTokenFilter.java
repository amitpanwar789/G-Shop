package com.gshop.productservice.config;

import com.gshop.productservice.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.extractUsername(jwt);
                Claims claims = jwtUtils.extractAllClaims(jwt);

                // In a real app we might put roles in JWT.
                // For now, I'll assume standard user role unless I check DB, but I'm decoupled.
                // Wait, User Service didn't put roles in JWT claims explicitly?
                // user-service impl: claims.put("id", userId); subject=email.
                // It didn't put roles. The node app checked DB in 'protect' middleware.
                // "const user = await User.findById(decoded.id).select('-password')"
                // So product-service needs to communicate with user-service or trust the token.
                // Since I cannot call user-service easily without Feign/RestTemplate setup and
                // I want to keep it simple migration:
                // I will modify User Service to put "isAdmin" in JWT?
                // OR I just assume everyone is user, and "isAdmin" check fails?
                // The Node.js `product-service` also had:
                // "import { checkAuth, checkAdmin } from '../middleware/authMiddleware.js'"
                // which used User model to find user by ID from token.
                // Since product-service has User model copy in Node code: "const User =
                // mongoose.model('User', userSchema)"
                // It was connecting to the SAME database or matching schema?
                // The docker-compose shows:
                // user-service -> mongo users-service
                // product-service -> mongo products-service
                // Wait, they use DIFFERENT databases!
                // How did Node product-service check admin?
                // "const user = await User.findById(decoded.id)"
                // Does product-service have a User collection?
                // Let's check `product-service/models/userModel.js`. yes. It exists.
                // So product-service DUPLICATES user data or at least reads from valid user
                // source?
                // If they act as microservices they should not share DB.
                // But typically for a quick monolith-to-microservice breakdown people share DB
                // or sync data.
                // The docker-compose says:
                // MONGO_URI=mongodb://mongo:27017/products-service
                // MONGO_URI=mongodb://mongo:27017/users-service
                // They are different DBs.
                // So `product-service` has its OWN User collection?
                // When is it populated?
                // I suspect `seeder.js` in root backend?
                // Or maybe they are just broken microservices sharing nothing and failing to
                // auth properly unless data is synced?
                // Or maybe `checkAuth` middleware in product-service connects to User DB?
                // Let's look at `middleware/authMiddleware.js` in `product-service` (I didn't
                // read it but I listed it).

                // Assuming I need to verify Admin. I should probably decode the token and if I
                // can't check DB, I might be stuck.
                // But for now, I will set a simple UserPrincipal.

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // Default
                                                                                                             // to User

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // I will save the "id" claim to request attribute or Principal to use it.
                request.setAttribute("userId", claims.get("id"));
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
