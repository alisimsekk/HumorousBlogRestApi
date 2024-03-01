package com.alisimsek.HumorousBlog.dto.response;

import com.alisimsek.HumorousBlog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String mail;
    private String image;

    public UserResponse(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setMail(user.getMail());
        setImage(user.getImage());
    }

    /*public String getImage() {
        return this.image == null ? "default.png" : this.image;
    }*/
}
