package com.microdockerkubernetes.msvcusers.service;

import com.microdockerkubernetes.msvcusers.models.entity.Users;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Users> getUsersAll();
    List<Users> getAllUsersIds(Iterable<Long> ids);
    Optional<Users> getUserId(Long id);
    Optional<Users> getUserEmail(String email);
    Users saveUser(Users users);
    void deleteUser(Long id);

}
