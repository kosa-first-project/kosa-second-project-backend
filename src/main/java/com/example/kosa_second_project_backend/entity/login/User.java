package com.example.kosa_second_project_backend.entity.login;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.Id;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User{
    // ID값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자가 작성한 회원 ID
    private String userId;

    // 회원 비밀번호
    private String password;

    // 회원 이름
    private String username;

    // 회원 전화번호
    private String phone;

    // 회원 성별
    private String gender;

    // 회원 닉네임
    private String nickname;

    // 회원 이메일
    private String email;

    // 회원가입 일 및 시간
    private LocalDateTime createDate;
    
    // 사용자와 관리자 구분
    private String roles;
}