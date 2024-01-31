package com.alisimsek.HumorousBlog.validation;

import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueMailValidator implements ConstraintValidator<UniqueMail,String> {
    public final UserRepository userRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> userFromDb = userRepository.findByMail(s);
        return userFromDb.isEmpty();
    }
}
