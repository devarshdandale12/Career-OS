package com.devarsh.careeros.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Look for the header:  Authorization: Bearer <token>
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            // 2. Cut off the word "Bearer " (7 characters) to get the token itself
            String token = header.substring(7);

            // 3. Only continue if the token is genuine and not expired
            if (jwtService.isTokenValid(token)) {
                String email = jwtService.extractEmail(token);

                try {
                    // 4. Load the user from the database
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    // 5. Disabled accounts are not recognized, even with a valid token
                    if (userDetails.isEnabled()) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        // 6. Write down who is calling for this request
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (UsernameNotFoundException e) {
                    // The user no longer exists: treat the request as "not logged in"
                }
            }
        }

        // 7. Pass the request on to the next step, whatever happened above
        filterChain.doFilter(request, response);
    }
}