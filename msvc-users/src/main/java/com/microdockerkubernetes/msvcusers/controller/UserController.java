package com.microdockerkubernetes.msvcusers.controller;

import com.microdockerkubernetes.msvcusers.models.entity.Users;
import com.microdockerkubernetes.msvcusers.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        if (userId.isPresent()) {
            return ResponseEntity.ok(userId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUser(@Valid @RequestBody Users users, BindingResult result) {
        if(!users.getName().isEmpty() && userService.getUserEmail(users.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("Mensaje:", "Ya existe el usuario con el correo electronico"));
        }
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(users));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody Users users, @PathVariable Long id) {
        Optional<Users> userId = userService.getUserId(id);
        if (userId.isPresent()) {
            Users users1 = userId.get();
            users1.setName(users.getName());
            users1.setEmail(users.getEmail());
            users1.setPassword(users.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(users1));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<Users> users = userService.getUserId(id);
        if (users.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users-courses")
    public ResponseEntity<?> getUsersCourses(@RequestParam List<Long> ids){
        return ResponseEntity.ok(userService.getAllUsersIds(ids));
    }

    @GetMapping("/authorized")
    public Map<String, Object> authorized(@RequestParam String code) {
        return Collections.singletonMap("code", code);
    }
}
