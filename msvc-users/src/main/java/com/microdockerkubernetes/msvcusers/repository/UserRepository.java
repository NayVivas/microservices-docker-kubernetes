package com.microdockerkubernetes.msvcusers.repository;

import com.microdockerkubernetes.msvcusers.models.entity.Users;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

@Resource
public interface UserRepository extends JpaRepository<Users, Long> {
}
