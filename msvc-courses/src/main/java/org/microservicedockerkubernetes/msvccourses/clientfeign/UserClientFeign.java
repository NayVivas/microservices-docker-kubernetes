package org.microservicedockerkubernetes.msvccourses.clientfeign;

import org.microservicedockerkubernetes.msvccourses.models.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-users", url = "http://msvc-users:8001/api/user")
public interface UserClientFeign {
    @GetMapping("/{id}")
    public Users getUserId(@PathVariable Long id);

    @PostMapping("/save")
    public Users create(@RequestBody Users users);

    @GetMapping("/users-courses")
    public List<Users> getUsersCourses(@RequestParam Iterable<Long> ids);
}
