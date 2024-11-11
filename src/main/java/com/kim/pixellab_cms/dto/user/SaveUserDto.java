package com.kim.pixellab_cms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class SaveUserDto {

    @NotEmpty(message = "The name is required.")
    @Size(min = 2, max = 100, message = "The length of name must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "The surname is required.")
    @Size(min = 2, max = 100, message = "The length of surname must be between 2 and 100 characters.")
    private String surname;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.")
    private String email;

    @NotEmpty(message = "The password is required.")
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;

    @NotEmpty(message = "The phone number is required.")
    @Pattern(regexp = "\\+?[0-9]*", message = "The phone number is invalid. It should contain only digits and may start with a plus sign.")
    @Size(min = 7, max = 15, message = "The phone number must be between 7 and 15 digits long.")
    private String phoneNumber;

    public SaveUserDto setName(String name) {
        this.name = name;
        return this;
    }

    public SaveUserDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public SaveUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public SaveUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public SaveUserDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

