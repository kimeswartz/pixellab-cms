package com.kim.pixellab_cms.dto.user;

import java.time.LocalDateTime;

public class ResponseUserDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer roleId; // For role based Logic

    public Long getId() {
        return id;
    }

    public ResponseUserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ResponseUserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public ResponseUserDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ResponseUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ResponseUserDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ResponseUserDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ResponseUserDto setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public ResponseUserDto setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

}
