package com.example.kosa_second_project_backend.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestBody;
        try {
            // 요청 본문(JSON)을 String으로 읽기
            requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            System.out.println("Request Body: " + requestBody);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read request body", e);
        }

        // JSONObject로 JSON 데이터 파싱
        JSONObject jsonObject = new JSONObject(requestBody);

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        //스프링 시큐리티에서 username과 password를 검증하기 위해서 token에 담는다.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //authenicationManager에 저장
        return authenticationManager.authenticate(authToken);
    }



    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
