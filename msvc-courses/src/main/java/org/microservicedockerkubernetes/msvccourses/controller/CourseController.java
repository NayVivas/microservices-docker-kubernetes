package org.microservicedockerkubernetes.msvccourses.controller;

import feign.FeignException;
import org.microservicedockerkubernetes.msvccourses.models.Users;
import org.microservicedockerkubernetes.msvccourses.models.entity.Course;
import org.microservicedockerkubernetes.msvccourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourseAll(){
        return ResponseEntity.ok(courseService.getAllCourse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseId(@PathVariable Long id, @RequestHeader(value = "Authorization", required = true) String token){
        Optional<Course> course = courseService.getCourseIdUsers(id, token);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(@RequestBody Course course){
        Course course1 = courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody Course course, @PathVariable Long id){
        Optional<Course> course1 = courseService.getCourseId(id);
        if (course1.isPresent()) {
            Course course2 = course1.get();
            course2.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(course2));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        Optional<Course> course1 = courseService.getCourseId(id);
        if(course1.isPresent()){
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody Users users, @PathVariable Long courseId, @RequestHeader(value = "Authorization", required = true) String token){
        Optional<Users> users1;
        try{
            users1 = courseService.assignUser(users, courseId, token);
        } catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Mensaje:", "No existe el usuario o error en la comunicacion " + e.getMessage()));
        }
        if(users1.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(users1.get());
        }
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody Users users, @PathVariable Long courseId, @RequestHeader(value = "Authorization", required = true) String token){
        Optional<Users> users1;
        try {
            users1 = courseService.createUser(users, courseId, token);
        } catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Mensaje:", "Error en la comunicacion, no se pudo crear el usuario" + e.getMessage()));
        }
        if(users1.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(users1.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove-user/{courseId}")
    public ResponseEntity<?> removeUser(@RequestBody Users users, @PathVariable Long courseId, @RequestHeader(value = "Authorization", required = true) String token){
        Optional<Users> users1;
        try{
            users1 = courseService.deleteUserCourse(users, courseId, token);
        } catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("Mensaje:", "No existe el usuario o error en la comunicacion " + e.getMessage()));
        }
        if(users1.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(users1.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteCourseUserId(@PathVariable Long id){
        courseService.deleteCourseUserId(id);
        return ResponseEntity.noContent().build();
    }
}