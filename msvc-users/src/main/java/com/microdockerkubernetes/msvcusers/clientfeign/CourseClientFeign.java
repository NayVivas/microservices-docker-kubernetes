package com.microdockerkubernetes.msvcusers.clientfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-courses")
public interface CourseClientFeign {
    @DeleteMapping("/api/course/delete-user/{id}")
    void deleteCourseUserId(@PathVariable Long id);

}
