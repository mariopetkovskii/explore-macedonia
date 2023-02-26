package com.example.exploremacedoniabackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.exploremacedoniabackend.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            ArrayNode claims = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getClaim(CLAIM_AUTHORITY).as(ArrayNode.class);

            List<GrantedAuthority> authorities = new ArrayList<>();
            claims.elements().forEachRemaining(claim -> authorities.add(new SimpleGrantedAuthority(claim.asText())));

            if (email != null) {
                email = email.toLowerCase();
                var user = userService.findByEmail(email);

                return new UsernamePasswordAuthenticationToken(email, null, authorities);
            }
            return null;
        }
        return null;
    }
}