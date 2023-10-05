package org.microservicedockerkubernetes.msvccourses.service.implement;

import org.microservicedockerkubernetes.msvccourses.clientfeign.UserClientFeign;
import org.microservicedockerkubernetes.msvccourses.models.Users;
import org.microservicedockerkubernetes.msvccourses.models.entity.Course;
import org.microservicedockerkubernetes.msvccourses.models.entity.CourseUser;
import org.microservicedockerkubernetes.msvccourses.repository.CourseRepository;
import org.microservicedockerkubernetes.msvccourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseImplement implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserClientFeign userClientFeign;
    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getCourseId(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCourseUserId(Long id) {
        courseRepository.deleteCourseUserId(id);
    }

    @Override
    public Optional<Course> getCourseIdUsers(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()){
            Course course1 = course.get();
            if(!course1.getCourseUser().isEmpty()){
                List<Long> ids = course1.getCourseUser().stream().map(CourseUser::getUserId).toList();
                List<Users> users = userClientFeign.getUsersCourses(ids);
                course1.setUsers(users);
            }
            return Optional.of(course1);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Users> assignUser(Users users, Long courseId) {
       Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            Users usersMsvc = userClientFeign.getUserId(users.getId());
            Course course1 = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(usersMsvc.getId());
            course1.addCourseUser(courseUser);
            courseRepository.save(course1);
            return Optional.of(usersMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Users> createUser(Users users, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            Users usersNewMsvc = userClientFeign.create(users);
            Course course1 = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(usersNewMsvc.getId());
            course1.addCourseUser(courseUser);
            courseRepository.save(course1);
            return Optional.of(usersNewMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Users> deleteUserCourse(Users users, Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            Users usersMsvc = userClientFeign.getUserId(users.getId());
            Course course1 = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(usersMsvc.getId());
            course1.deleteCourseUser(courseUser);
            courseRepository.save(course1);
            return Optional.of(usersMsvc);
        }
        return Optional.empty();
    }
}
