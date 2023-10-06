package com.microdockerkubernetes.msvcusers.clientfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-courses", url = "host.docker.internal:8002/api/course")
public interface CourseClientFeign {
    @DeleteMapping("/delete-user/{id}")
    void deleteCourseUserId(@PathVariable Long id);

}
