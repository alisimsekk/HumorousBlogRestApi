package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.dto.UserCreateDto;
import com.alisimsek.HumorousBlog.dto.UserProjection;
import com.alisimsek.HumorousBlog.email.EmailService;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.exception.ActivationNotificationException;
import com.alisimsek.HumorousBlog.exception.InvalidTokenException;
import com.alisimsek.HumorousBlog.exception.NotUniqueMailException;
import com.alisimsek.HumorousBlog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Page<UserProjection> getAllUsers(Pageable page) {
        return userRepository.getAllUserRecords(page);
    }

    @Transactional(rollbackOn = MailException.class )
    public User createUser(UserCreateDto userCreateDto) {
        User user = userCreateDto.toUser();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            User newUser =userRepository.saveAndFlush(user);
            emailService.sendActivationMail(newUser.getMail(),newUser.getActivationToken());
            return newUser;
        }catch (DataIntegrityViolationException ex){
            throw new NotUniqueMailException();
        }catch (MailException ex){
            throw new ActivationNotificationException();
        }
    }

    public void activateUser(String token) {
        User userFromDbByToken = userRepository.findByactivationToken(token);
        if (userFromDbByToken == null){
            throw new InvalidTokenException();
        }
        userFromDbByToken.setActive(true);
        userFromDbByToken.setActivationToken(null);
        userRepository.save(userFromDbByToken);
    }
}
