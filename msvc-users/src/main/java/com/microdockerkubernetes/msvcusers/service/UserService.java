package com.microdockerkubernetes.msvcusers.service;

import com.microdockerkubernetes.msvcusers.models.entity.Users;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Users> getUsersAll();
    Optional<Users> getUserId(Long id);
    Users saveUser(Users users);
    void deleteUser(Long id);
}
