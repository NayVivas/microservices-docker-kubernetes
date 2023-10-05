package org.microservicedockerkubernetes.msvccourses.service;

import org.microservicedockerkubernetes.msvccourses.models.Users;
import org.microservicedockerkubernetes.msvccourses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourse();
    Optional<Course> getCourseId(Long id);
    Course saveCourse(Course course);
    void deleteCourse(Long id);
    void deleteCourseUserId(Long id);
    Optional<Course> getCourseIdUsers(Long id);

    Optional<Users> assignUser(Users users, Long courseId);
    Optional<Users> createUser(Users users, Long courseId);
    Optional<Users> deleteUserCourse(Users users, Long courseId);
}
