package com.microdockerkubernetes.msvcusers.service.implement;

import com.microdockerkubernetes.msvcusers.clientfeign.CourseClientFeign;
import com.microdockerkubernetes.msvcusers.models.entity.Users;
import com.microdockerkubernetes.msvcusers.repository.UserRepository;
import com.microdockerkubernetes.msvcusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserImplement implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseClientFeign courseClientFeign;
    @Override
    @Transactional(readOnly = true)
    public List<Users> getUsersAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public List<Users> getAllUsersIds(Iterable<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Users> getUserId(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<Users> getUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Users saveUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        courseClientFeign.deleteCourseUserId(id);
    }
}
