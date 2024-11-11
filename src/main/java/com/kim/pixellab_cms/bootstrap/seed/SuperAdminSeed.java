package com.kim.pixellab_cms.bootstrap.seed;

// Create a SUPER_ADMIN role upon startup of the application if not exist in db

import com.kim.pixellab_cms.dto.user.SaveUserDto;
import com.kim.pixellab_cms.entity.role.Role;
import com.kim.pixellab_cms.entity.role.RoleEnum;
import com.kim.pixellab_cms.entity.user.User;

import com.kim.pixellab_cms.repository.user.UserRepository;
import com.kim.pixellab_cms.repository.role.RoleRepository;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SuperAdminSeed implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public SuperAdminSeed(
            @NonNull UserRepository userRepository,
            @NonNull RoleRepository roleRepository,
            @NonNull PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        this.createManager();
    }

    private void createManager() {
        SaveUserDto saveUserDto = new SaveUserDto();
        saveUserDto
                .setName("Super")
                .setSurname("Admin")
                .setEmail("super@email.com")
                .setPhoneNumber("0700123456")
                .setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(saveUserDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        user.setName(saveUserDto.getName());
        user.setSurname(saveUserDto.getSurname());
        user.setEmail(saveUserDto.getEmail());
        user.setPhoneNumber(saveUserDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(saveUserDto.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}
