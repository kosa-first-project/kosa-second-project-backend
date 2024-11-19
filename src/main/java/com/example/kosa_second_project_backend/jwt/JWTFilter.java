package com.example.kosa_second_project_backend.jwt;

import com.example.kosa_second_project_backend.dto.MyUserDetails;
import com.example.kosa_second_project_backend.entity.login.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtutil;

    public JWTFilter(JWTUtil jwtutil) {
        this.jwtutil = jwtutil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("authorizationHeader1 " + authorizationHeader);
            filterChain.doFilter(request, response);

            return;
        }
        System.out.println("authorizationHeader2 " + authorizationHeader);

        String token = authorizationHeader.split(" ")[1];

        if(jwtutil.isExpired(token)) {
            System.out.println("Expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String username = jwtutil.getUsername(token);
        String role = jwtutil.getRole(token);

        User user = new User();
        user.setUsername(username);
        user.setPassword("tempassword");
        user.setRoles(role);

        MyUserDetails myUserDetails = new MyUserDetails(user);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
