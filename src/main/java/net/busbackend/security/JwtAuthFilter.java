package net.busbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.busbackend.entites.User;
import net.busbackend.repos.UserRepository;
import net.busbackend.util.Constants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    // Utility class to parse and validate JWT
    private final JwtUtil jwtUtil;

    // Repository to fetch user from database
    private final UserRepository userRepository;


    // Cookie attribute used while clearing cookies
    private final String cookieSameSiteAttribute = Constants.SAME_SITE_ATTRIBUTE;

    // Constructor injection
    public JwtAuthFilter(JwtUtil jwtUtil, UserRepository userRepository
                         ) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Get request path
        String path = req.getRequestURI();

        // Token initially null
        String token = null;

        // Extract JWT from HttpOnly cookie named "accessToken"
        if (req.getCookies() != null) {
            for (var cookie : req.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // If token not found, skip authentication
        if (token == null) {
            chain.doFilter(req, res);
            return;
        }

        try {
            // Parse JWT and extract claims
            Claims claims = jwtUtil.parseClaims(token);

            // Get userId stored in token
            String userId = claims.getSubject();

            // Get token version for logout-all-devices feature
            int tokenVer = claims.get("ver", Integer.class);

            // Fetch user from database
            User user = userRepository.findById(userId).orElse(null);

            // If user doesn't exist or token version mismatch
            if (user == null || user.getTokenVersion() != tokenVer) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Token invalid or expired");
                return;
            }

            // Set authentication in Spring Security context
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of());

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (JwtException e) {
            log.warn("JWT error: {}", e);
        }

        // Continue filter chain
        chain.doFilter(req, res);
    }
}
