package com.example.kosa_second_project_backend.service;

import com.example.kosa_second_project_backend.dto.MyUserDetails;
import com.example.kosa_second_project_backend.entity.login.User;
import com.example.kosa_second_project_backend.repository.login.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUsername(username);
        if (userData == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        System.out.println(userData.getUsername());
        System.out.println(userData.getPassword());
        return new MyUserDetails(userData);
    }
}
