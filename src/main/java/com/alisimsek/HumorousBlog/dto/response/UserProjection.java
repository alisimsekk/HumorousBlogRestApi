package com.alisimsek.HumorousBlog.dto.response;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {
    Long getId();
    String getUsername();
    String getMail();
    @Value("#{target.image != null ? target.image : 'default.png'}")
    String getImage();
}
