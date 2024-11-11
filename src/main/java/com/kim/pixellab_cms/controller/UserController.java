package com.kim.pixellab_cms.controller;

import com.kim.pixellab_cms.dto.user.ResponseUserDto;
import com.kim.pixellab_cms.dto.user.SaveUserDto;

import com.kim.pixellab_cms.entity.user.User;
import com.kim.pixellab_cms.service.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseUserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        ResponseUserDto userResponseDto = userService.convertToResponseDto(currentUser);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<ResponseUserDto>> allUsers() {
        List<ResponseUserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody SaveUserDto saveUserDto) {
        ResponseUserDto createdUser = userService.createUser(saveUserDto);
        return ResponseEntity.status(201).body(createdUser); // 201 Created
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long id, @RequestBody SaveUserDto saveUserDto) {
        try {
            ResponseUserDto updatedUser = userService.updateUser(id, saveUserDto);
            return ResponseEntity.ok(updatedUser); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseUserDto> findUserById(@PathVariable Long id) {
        Optional<ResponseUserDto> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not Found
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}