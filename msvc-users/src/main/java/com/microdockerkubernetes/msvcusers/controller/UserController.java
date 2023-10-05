package com.microdockerkubernetes.msvcusers.controller;

import com.microdockerkubernetes.msvcusers.models.entity.Users;
import com.microdockerkubernetes.msvcusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getUsersAll() {
        return userService.getUsersAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserId(@PathVariable Long id) {
        Optional<Users> userId = userService.getUserId(id);
        if(userId.isPresent()) {
            return ResponseEntity.ok(userId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users saveUser(@RequestBody Users users) {
        return userService.saveUser(users);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@RequestBody Users users, @PathVariable Long id) {
        Optional<Users> userId = userService.getUserId(id);
        if(userId.isPresent()) {
            Users users1 = userId.get();
            users1.setName(users.getName());
            users1.setEmail(users.getEmail());
            users1.setPassword(users.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(users1));
        }
        return ResponseEntity.notFound().build();
    }
}
