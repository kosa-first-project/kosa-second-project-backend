package com.example.kosa_second_project_backend.dto;

import com.example.kosa_second_project_backend.entity.login.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


public class MyUserDetails implements UserDetails {
    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 역할 정보를 GrantedAuthority로 변환
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return user.getRoles();
                // roles 필드 값 반환
            }
        });
        return collection;
    }

    public String getUserId() {
        return user.getUserId();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // 유저 이름 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (true로 설정)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (true로 설정)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부 (true로 설정)
    }

    @Override
    public boolean isEnabled() {
        return true; // 활성화 여부 (true로 설정)
    }

    // Optional: 사용자 정보를 반환하기 위한 추가 메서드
    public String getEmail() {
        return user.getEmail();
    }

    public String getPhone() {
        return user.getPhone();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public String getGender() {
        return user.getGender();
    }

    public LocalDateTime getCreateDate() {
        return user.getCreateDate();
    }
}
