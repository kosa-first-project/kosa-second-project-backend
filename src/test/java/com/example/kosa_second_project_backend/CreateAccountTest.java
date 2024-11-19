package com.example.kosa_second_project_backend;

import com.example.kosa_second_project_backend.entity.login.User;
import com.example.kosa_second_project_backend.repository.login.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class CreateAccountTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    @Transactional
    void save(){
        User testUser = new User();
        LocalDateTime now = LocalDateTime.now();
        testUser.setPassword(bCryptPasswordEncoder.encode("admin1"));
        testUser.setUsername("admin1");
        testUser.setPhone("010-0000-0020");
        testUser.setGender("Male");
        testUser.setEmail("admin1@gmail.com");
        testUser.setNickname("admin1");
        testUser.setCreateDate(now);
        testUser.setRoles("admin");
        userRepository.save(testUser);
    }
}
