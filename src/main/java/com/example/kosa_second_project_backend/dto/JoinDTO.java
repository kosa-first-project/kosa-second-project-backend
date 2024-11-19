package com.example.kosa_second_project_backend.dto;

import com.example.kosa_second_project_backend.entity.login.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class JoinDTO {
    private Long id;          // ID값
    private String email;     // 회원 이메일
    private String password;  // 회원 비밀번호
    private String username;  // 회원 이름
    private String phone;     // 회원 전화번호
    private String gender;    // 회원 성별
    private String nickname;  // 회원 닉네임
    private LocalDateTime createDate; // 회원가입 일 및 시간
    private String roles;     // 사용자와 관리자 구분

    public User toEntity() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setPhone(phone);
        user.setGender(gender);
        user.setNickname(nickname);
        user.setRoles(roles);
        user.setCreateDate(LocalDateTime.now());
        return user;
    }
}
