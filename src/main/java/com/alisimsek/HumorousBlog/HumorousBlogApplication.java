package com.alisimsek.HumorousBlog;

import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HumorousBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumorousBlogApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner userCreator(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				for (var i =1; i<= 25; i++){
					User user = new User();
					user.setUsername("user"+i);
					user.setMail("user"+i+"@mail.com");
					user.setPassword(passwordEncoder.encode("P4assword"));
					user.setActive(true);
					userRepository.save(user);
				}
				User user = new User();
				user.setUsername("user26");
				user.setMail("user26@mail.com");
				user.setPassword(passwordEncoder.encode("P4assword"));
				user.setActive(false);
				userRepository.save(user);
			}
		};

	}
}
