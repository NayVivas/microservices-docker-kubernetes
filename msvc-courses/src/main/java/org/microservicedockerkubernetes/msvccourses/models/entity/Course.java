package org.microservicedockerkubernetes.msvccourses.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.microservicedockerkubernetes.msvccourses.models.Users;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cursos")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;
    @Getter
    @Transient
    private List<Users> users;
    public Course() {
        courseUsers = new ArrayList<>();
        users = new ArrayList<>();
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<CourseUser> getCourseUser() {
        return courseUsers;
    }
    public void setCourseUser(List<CourseUser> courseUser) {
        this.courseUsers = courseUser;
    }
    public void addCourseUser(CourseUser courseUser){
        courseUsers.add(courseUser);
    }
    public void deleteCourseUser(CourseUser courseUser){
        courseUsers.remove(courseUser);
    }
    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
