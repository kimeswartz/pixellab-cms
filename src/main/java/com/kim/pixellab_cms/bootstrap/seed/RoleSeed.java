package com.kim.pixellab_cms.bootstrap.seed;

// Using Spring Boot system which allows executing some actions on the application startup.
// This function seeds predefined roles into the database at application startup if they don't already exist.

import com.kim.pixellab_cms.entity.role.Role;
import com.kim.pixellab_cms.entity.role.RoleEnum;
import com.kim.pixellab_cms.repository.role.RoleRepository;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleSeed implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;


    public RoleSeed(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] {RoleEnum.SUPER_ADMIN, RoleEnum.ADMIN, RoleEnum.USER};
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.SUPER_ADMIN, "Manager role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.USER, "Default user role"
        );

        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToAdd = new Role();

                roleToAdd.setName(roleName);
                roleToAdd.setDescription(roleDescriptionMap.get(roleName));
                roleRepository.save(roleToAdd);
            });
        });
    }
}
