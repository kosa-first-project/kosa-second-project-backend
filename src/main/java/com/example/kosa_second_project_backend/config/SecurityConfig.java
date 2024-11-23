package com.example.kosa_second_project_backend.config;

import com.example.kosa_second_project_backend.jwt.JwtFilter;
import com.example.kosa_second_project_backend.jwt.JwtUtil;
import com.example.kosa_second_project_backend.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtUtil jwtUtil;

    private final WebConfig webConfig;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, WebConfig webConfig) {

        this.authenticationConfiguration = authenticationConfiguration;

        this.jwtUtil = jwtUtil;

        this.webConfig = webConfig;
    }

    // 비밀번호 인코딩 진행
    @Bean
    public BCryptPasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // **Allowed Origins:** Specify the frontend URL(s)
        configuration.setAllowedOriginPatterns(List.of("http://localhost:5173"));

        // **Allowed Methods:** Define HTTP methods that are allowed
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // **Allowed Headers:** Specify which headers are allowed in requests
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        // **Allow Credentials:** If your requests include credentials (e.g., cookies)
        configuration.setAllowCredentials(true);

        // **Exposed Headers:** (Optional) Specify which headers are exposed to the client
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration); // Apply to all endpoints
        return source;
    }

    // HttpSecurity 설정을 SecurityFilterChain Bean으로 변경
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {

        http
                .csrf((auth) -> auth.disable());// CSRF 비활성화

        http
                .formLogin((formLogin) -> formLogin.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()  // 회원가입과 로그인은 인증 없이 접근 허용
                        .requestMatchers("/admin").hasAuthority("admin")
                        .anyRequest().authenticated()// 나머지 요청은 인증 필요
                )
                .logout(withDefaults());
        http
                .addFilterBefore(new JwtFilter(), LogoutFilter.class);

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // 세션 관리 비활성화 (JWT 사용 시)

        return http.build();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}

//package com.example.kosa_second_project_backend.config;
//
//import com.example.kosa_second_project_backend.jwt.JwtFilter;
//import com.example.kosa_second_project_backend.jwt.JwtUtil;
//import com.example.kosa_second_project_backend.jwt.LoginFilter;
//import com.example.kosa_second_project_backend.service.MyUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import java.util.Arrays;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final AuthenticationConfiguration authenticationConfiguration;
//    private final JwtUtil jwtUtil;
//    private final MyUserDetailsService userDetailsService;
//
//    @Autowired
//    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
//        this.authenticationConfiguration = authenticationConfiguration;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    // **비밀번호 인코더 Bean**
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // **AuthenticationManager Bean**
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    // **CORS Configuration Source Bean**
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        // **허용할 출처 패턴 설정**
//        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173"));
//        // 필요에 따라 여러 출처를 추가할 수 있습니다.
//        // configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173", "https://*.yourdomain.com"));
//
//        // **허용할 HTTP 메서드 설정**
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//
//        // **허용할 요청 헤더 설정**
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//
//        // **자격 증명 허용**
//        configuration.setAllowCredentials(true);
//
//        // **노출할 응답 헤더 (선택사항)**
//        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // 모든 엔드포인트에 적용
//        return source;
//    }
//
//    // **JwtFilter Bean 정의**
//    @Bean
//    public JwtFilter jwtFilter() {
//        return new JwtFilter(jwtUtil, userDetailsService);
//    }
//
//    // **LoginFilter Bean 정의**
//    @Bean
//    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
//        return new LoginFilter(authenticationManager, jwtUtil);
//    }
//
//    // **Security Filter Chain Configuration**
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource, JwtFilter jwtFilter, LoginFilter loginFilter) throws Exception {
//        http
//                // **CORS 설정 적용**
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
//
//                // **CSRF 비활성화**
//                .csrf(csrf -> csrf.disable())
//
//                // **폼 로그인 및 HTTP Basic 인증 비활성화**
//                .formLogin(formLogin -> formLogin.disable())
//                .httpBasic(httpBasic -> httpBasic.disable())
//
//                // **요청 권한 설정 (순서 중요)**
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/admin").hasAuthority("admin") // 관리자 권한이 필요한 엔드포인트
//                        .requestMatchers("/api/guides/GuideMain").permitAll() // 공개 엔드포인트
//                        .anyRequest().authenticated() // 나머지 엔드포인트는 인증 필요
//                )
//
//                // **로그아웃 설정**
//                .logout(withDefaults())
//
//                // **세션 관리 비활성화 (JWT 사용 시)**
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // **필터 추가**
//                .addFilterBefore(jwtFilter, LogoutFilter.class)
//                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
