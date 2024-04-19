package com.alisimsek.HumorousBlog.configuration;

import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Optional<User> userFromDb = userService.findUserByMail(mail);

        if (userFromDb.isEmpty()){
            throw new UsernameNotFoundException(mail + " is not found");
        }

        return new CurrentUser(userFromDb.get());
    }
}
