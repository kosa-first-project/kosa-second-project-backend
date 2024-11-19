package com.example.kosa_second_project_backend.repository.login;


import com.example.kosa_second_project_backend.entity.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    //계정 생성은 JpaRepository에 이미 포함되어 있음

    //아이디 존재 확인
    Boolean existsByUsername(String username);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User findByEmail(String email);
}
