package com.example.exploremacedoniabackend.security;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import com.example.exploremacedoniabackend.models.userroles.exceptions.AccessForbiddenException;
import com.example.exploremacedoniabackend.service.userroles.interfaces.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.exploremacedoniabackend.security.SecurityConstants.LOGIN_URL;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final JWTUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JWTUtils jwtUtils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_URL, "POST"));
        this.jwtUtils = jwtUtils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            String auth = req.getHeader("Authorization");

            if (auth == null || !auth.startsWith("Basic ")) {
                throw new AccessForbiddenException();
            }

            String credentials = Base64Coder.decodeString(auth.substring(6));
            String[] emailPassword = credentials.split(":", 2);

            String email = emailPassword[0];

            if (email != null) {
                email = email.toLowerCase();
            }

            String password = emailPassword[1];
            User user = userService.findByEmail(email);
            if(user==null){
                throw new AccessForbiddenException();
            }

            List<GrantedAuthority> authorities = jwtUtils.addAuthoritiesFromRoles(user, password);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            password,
                            authorities)
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        jwtUtils.createAndAppendToken(req, res, chain, auth);
    }
}