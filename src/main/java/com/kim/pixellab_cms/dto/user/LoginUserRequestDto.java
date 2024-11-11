package com.kim.pixellab_cms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginUserRequestDto {

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.")
    private String email;

    @NotEmpty(message = "The password is required.")
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;

    public String getEmail() {
        return email;
    }

    public LoginUserRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

}
