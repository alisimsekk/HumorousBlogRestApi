package com.alisimsek.HumorousBlog.dto.request;

import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.validation.UniqueMail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        //custom message - (message = "{humorous.constraints.NotBlank}"
        @NotBlank(message = "{humorous.constraint.username.notblank}")
        @Size(min = 4, max = 255)
        String username,

        @Size(min = 8, max = 255)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{humorous.constraint.password.pattern}")
        String password,

        @NotBlank
        @Email
        //custom validation - @UniqueMail
        @UniqueMail
        String mail
) {

    public User toUser (){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setMail(mail);
        return user;
    }

}
