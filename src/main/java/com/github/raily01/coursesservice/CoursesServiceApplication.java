package com.github.raily01.coursesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.github.raily01.coursesservice.common.repository")
@SpringBootApplication
public class CoursesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesServiceApplication.class, args);
    }
}
