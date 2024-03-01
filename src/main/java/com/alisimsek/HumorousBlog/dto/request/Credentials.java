package com.alisimsek.HumorousBlog.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Credentials(@Email String mail, @NotBlank String password) {
}
