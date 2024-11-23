package com.example.kosa_second_project_backend.controller;


import com.example.kosa_second_project_backend.dto.JoinDTO;
import com.example.kosa_second_project_backend.dto.LoginRequest;
import com.example.kosa_second_project_backend.dto.MyUserDetails;
import com.example.kosa_second_project_backend.entity.login.CustomUser;
import com.example.kosa_second_project_backend.jwt.JwtUtil;
import com.example.kosa_second_project_backend.repository.login.UserRepository;
import com.example.kosa_second_project_backend.service.JoinService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
//@CrossOrigin(origins = "*")
public class AuthController {
//    @Autowired
//    private JoinService joinService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JWTUtil jwtUtil;
//    @Autowired
//    private UserRepository userRepository;

    //    @PostMapping("/api/join")
//    public ResponseEntity<String> CreateUser(@RequestBody JoinDTO joinDTO) {
//        System.out.println("join success");
//
//        if (joinService.register(joinDTO) != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공했습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
//        }
//    }
//
//   @PostMapping("/api/login")
//     public ResponseEntity<String> LoginUser(@RequestBody LoginRequest loginRequest) {
//        System.out.println("login success");
//
//         //사용자가 로그인 폼에 입력한 username과 password를 기반으로 UsernamePasswordAuthenticationToken 객체를 생성하여 인증을 시도
//         //UsernamePasswordAuthenticationToken 객체가 반환
//         //이 객체는 사용자의 권한(roles)과 추가 정보를 포함하여 SecurityContext에 저장
//         UsernamePasswordAuthenticationToken authenticationToken =
//                 new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
//
//         //authenticationManager를 사용해 주어진 인증 토큰(authenticationToken)을 기반으로 사용자 인증을 시도
//         //authenticationManager는 여러 AuthenticationProvider를 통해 인증을 처리할 수 있으며, 기본적으로 DaoAuthenticationProvider와 같은 프로바이더가 사용
//         //인증이 성공하면, Authentication 객체가 반환되며, 이 객체에는 인증된 사용자의 정보, 권한, 인증 상태 등이 포함
//         Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//         //인증이 완료된 후, SecurityContextHolder에 인증 정보를 설정하여 현재 실행 중인 스레드에서 인증된 사용자의 정보를 관리할 수 있다함.
//         SecurityContextHolder.getContext().setAuthentication(authentication);
//
//         // 사용자 정보 가져오기
//         MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
//
//         // JWT 생성
//         String token = jwtUtil.createJwt(userDetails.getUsername(), userDetails.getAuthorities().iterator().next().getAuthority(), 1000 * 60 * 60 * 10L);
//
//         MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
//
//         header.add("Authorization", token);
//
//         System.out.println("Generated Token: " + token);
//
//         // JWT를 ResponseEntity로 반환
//         return ResponseEntity.ok("Bearer " + header);
//     }
//}
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/api/auth/login")

    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> data1, HttpServletResponse response) {
        // 아이디와 비밀번호 가져오기
        var authToken = new UsernamePasswordAuthenticationToken(
                data1.get("username"), data1.get("password")
        );

        // 인증 수행
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("인증 성공");

        // JWT 생성
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        String jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("JWT 생성 성공 : " + jwt);

        // JWT를 쿠키에 저장
        var cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(60 * 60 * 24); // 하루 유효
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        // 사용자 정보 가져오기(CustomUser 사용)
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        String authorities = user.getAuthorities().stream()
                .map(data -> data.getAuthority())
                .collect(Collectors.joining(","));
        // 반환 데이터
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("jwt", jwt); // JWT
        responseBody.put("userId", user.getUserId());     // 사용자 ID
        responseBody.put("username", user.getUsername()); // 사용자 이름
        responseBody.put("roles", user.getAuthorities()); // 권한 정보

        return ResponseEntity.ok(responseBody);
    }

}
