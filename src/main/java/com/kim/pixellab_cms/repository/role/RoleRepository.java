package com.kim.pixellab_cms.repository.role;

import com.kim.pixellab_cms.entity.role.Role;
import com.kim.pixellab_cms.entity.role.RoleEnum;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
