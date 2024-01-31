package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.exception.NotUniqueMailException;
import com.alisimsek.HumorousBlog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {

        /*Optional<User> existUseerWithSameMail = userRepository.findByMail(user.getMail());
        if (existUseerWithSameMail.isPresent()){
            throw new RuntimeException("The User has already been saved.");
        }*/

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            throw new NotUniqueMailException();
        }
    }
}
