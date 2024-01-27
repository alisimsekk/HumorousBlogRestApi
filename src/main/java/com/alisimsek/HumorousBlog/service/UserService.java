package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User createUser(User user) {
        Optional<User> existUseerWithSameMail = userRepository.findByMail(user.getMail());
        if (existUseerWithSameMail.isPresent()){
            throw new RuntimeException("The User has already been saved.");
        }
        return userRepository.save(user);
    }
}
