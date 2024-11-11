package com.kim.pixellab_cms.service.user;

import com.kim.pixellab_cms.dto.user.SaveUserDto;
import com.kim.pixellab_cms.dto.user.ResponseUserDto;  // Corrected import for ResponseUserDto
import com.kim.pixellab_cms.entity.role.Role;
import com.kim.pixellab_cms.entity.role.RoleEnum;
import com.kim.pixellab_cms.entity.user.User;
import com.kim.pixellab_cms.repository.user.UserRepository;
import com.kim.pixellab_cms.repository.role.RoleRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create Operation
    public ResponseUserDto createUser(SaveUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role USER not found");
        }

        var user = new User()
                .setName(input.getName())
                .setSurname(input.getSurname())
                .setEmail(input.getEmail())
                .setPhoneNumber(input.getPhoneNumber())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setRole(optionalRole.get());

        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }

    // Update Operation
    public ResponseUserDto updateUser(Long id, SaveUserDto input) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(input.getName())
                    .setSurname(input.getSurname())
                    .setEmail(input.getEmail())
                    .setPhoneNumber(input.getPhoneNumber());

            // Password update check
            if (input.getPassword() != null && !input.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(input.getPassword()));
            }

            // Role is not updated
            User updatedUser = userRepository.save(existingUser);
            return convertToResponseDto(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // Retrieve user by ID
    public Optional<ResponseUserDto> findById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(this::convertToResponseDto);
    }

    // Retrieve all users
    public List<ResponseUserDto> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Delete Operation
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            user.setRole(null); // Remove association
            userRepository.save(user); // Save changes
            userRepository.delete(user); // Then delete user
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // Converts User entity to ResponseUserDto
    public ResponseUserDto convertToResponseDto(User user) {
        ResponseUserDto dto = new ResponseUserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setRoleId(user.getRole() != null ? user.getRole().getId() : null); // Handle potential null role
        return dto;
    }
}
