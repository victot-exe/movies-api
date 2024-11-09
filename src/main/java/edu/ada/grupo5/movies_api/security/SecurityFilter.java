package edu.ada.grupo5.movies_api.security;

import edu.ada.grupo5.movies_api.model.exception.StandardError;
import edu.ada.grupo5.movies_api.service.TokenService;
import edu.ada.grupo5.movies_api.service.UserService;
import edu.ada.grupo5.movies_api.service.exception.ResourceNotFoundException;
import edu.ada.grupo5.movies_api.service.exception.ValidationErrorException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var login = tokenService.isTokenValid(token);
                UserDetails userDetails = userService.findUserDetailsByLogin(login);

                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (ResourceNotFoundException e) {
            var standardError = StandardError.builder().timestamp(Instant.now())
                    .status(HttpStatus.NOT_FOUND.value()).error("Resource not found")
                    .message(e.getMessage()).path(request.getRequestURI()).build();
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType("application/json");
            response.getWriter().write(standardError.toString());
        } catch (ValidationErrorException e) {
            var standardError = StandardError.builder().timestamp(Instant.now())
                    .status(HttpStatus.FORBIDDEN.value()).error("Error validating")
                    .message(e.getMessage()).path(request.getRequestURI()).build();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write(standardError.toString());
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}