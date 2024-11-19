package com.example.kosa_second_project_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 스프링 시큐리티 의존성 무시
@EnableJpaRepositories(basePackages = {"com.example.kosa_second_project_backend.repository"})
@EntityScan(basePackages = {"com.example.kosa_second_project_backend.entity"})
public class KosaSecondProjectBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(KosaSecondProjectBackendApplication.class, args);
    }

}
