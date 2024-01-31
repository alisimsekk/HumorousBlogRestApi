package com.alisimsek.HumorousBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HumorousBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumorousBlogApplication.class, args);
		System.out.println("project run");
	}

}
