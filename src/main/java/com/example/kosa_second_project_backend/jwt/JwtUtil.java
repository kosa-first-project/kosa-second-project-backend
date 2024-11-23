package com.example.kosa_second_project_backend.jwt;

import com.example.kosa_second_project_backend.dto.MyUserDetails;
import com.example.kosa_second_project_backend.entity.login.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    // 해싱키 설정하기
    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
            ));

    // JWT 생성 매서드
    public static String createToken(Authentication auth) {
        System.out.println("여기까지 ok");
        System.out.println(auth.getPrincipal().getClass());
        System.out.println(auth.getPrincipal().equals(CustomUser.class));
        System.out.println(auth.getClass());

        // Cast to MyUserDetails instead of CustomUser
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

        // If MyUserDetails contains CustomUser
        // CustomUser customUser = userDetails.getCustomUser();

        // Extract authorities
        String authorities = userDetails.getAuthorities().stream()
                .map(data -> data.getAuthority())
                .collect(Collectors.joining(","));

        // Build JWT token
        String jwt = Jwts.builder()
                .claim("username", userDetails.getUsername())
                // Assuming MyUserDetails has getUserId()
                .claim("userID", userDetails.getUserId())
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 360000)) // Adjust expiration as needed
                .signWith(key) // Insert hashing key
                .compact();

        return jwt;

//        CustomUser user =(CustomUser)auth.getPrincipal();
//
//        user.getAuthorities().stream().map(data->data.getAuthority()).collect(Collectors.joining(","));
//
//        String jwt = Jwts.builder()
//                .claim("username", user.getUsername())
//                .claim("userID", user.getUserId())
//                .claim("authorities", user.getAuthorities())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 360000))
//                .signWith(key) //해싱 키 삽입
//                .compact();
//        return jwt;
    }

    // JWT 파싱 매서드
    public static Claims extractToken(String token) {
        return Jwts. parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}