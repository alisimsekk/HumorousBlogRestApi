package com.alisimsek.HumorousBlog.dto.request;

import com.alisimsek.HumorousBlog.validation.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate (
        @NotBlank(message = "{humorous.constraint.username.notblank}")
        @Size(min = 4, max = 255)
        String username,
        @FileType (types = {"jpeg", "png"})
        String image
) {
}
