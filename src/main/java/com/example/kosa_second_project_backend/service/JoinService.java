package com.example.kosa_second_project_backend.service;

import com.example.kosa_second_project_backend.dto.JoinDTO;
import com.example.kosa_second_project_backend.entity.login.User;
import com.example.kosa_second_project_backend.repository.login.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

        private final UserRepository userRepository;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
                this.userRepository = userRepository;
                this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        public User register(JoinDTO joinDTO) {
                User registerSuccess = null;
                if (userRepository.existsByUsername(joinDTO.getUsername())) {
                        return registerSuccess;
                } else {
                        User user = joinDTO.toEntity();
                        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                        registerSuccess = userRepository.save(user);
                        return registerSuccess;
                }
        }
}