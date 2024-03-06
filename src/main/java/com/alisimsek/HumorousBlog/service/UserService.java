package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.dto.request.UserCreate;
import com.alisimsek.HumorousBlog.dto.request.UserUpdate;
import com.alisimsek.HumorousBlog.dto.response.UserResponse;
import com.alisimsek.HumorousBlog.email.EmailService;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.exception.*;
import com.alisimsek.HumorousBlog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Lazy
    @Autowired
    private TokenService tokenService;

    public Page<UserResponse> findAllUsers(Pageable page, String authorizationHeader) {
        User loggedInUser = tokenService.verifyToken(authorizationHeader);
        if (loggedInUser == null){
            return userRepository.findAll(page).map(UserResponse::new);
        }
        return userRepository.findByIdNot(loggedInUser.getId(), page).map(UserResponse::new);
    }

    /*Projection
        public Page<UserProjection> getAllUsers(Pageable page) {
        return userRepository.getAllUserRecords(page);
    }*/

    public UserResponse findByUser(Long id) {
        return new UserResponse(userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,User.class)));
    }

    public Optional<User> findUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Transactional(rollbackOn = MailException.class )
    public User createUser(UserCreate userCreate) {
        User user = userCreate.toUser();
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


    public UserResponse update(Long id, UserUpdate userUpdate, String authorizationHeader) {
        var loggedInUser = tokenService.verifyToken(authorizationHeader);

        if (loggedInUser==null || loggedInUser.getId() != id){
            throw new AuthorizationException();
        }

        User userFromDb = userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id,User.class));
        userFromDb.setUsername(userUpdate.username());
        return new UserResponse(userRepository.save(userFromDb));
    }
}
