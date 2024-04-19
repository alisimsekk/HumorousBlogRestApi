package com.alisimsek.HumorousBlog.configuration;

import com.alisimsek.HumorousBlog.security.AuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // metot üzerinde anotasyon ile current user validation işlemi yapmaya yarar
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authentication) ->
                authentication.requestMatchers(AntPathRequestMatcher
                                .antMatcher(HttpMethod.PUT, "api/v1/user/{id}")).authenticated()
                        .anyRequest().permitAll()
        );
        http.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(
                new AuthEntryPoint()));

        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}