package org.microservicedockerkubernetes.msvccourses.clientfeign;

import org.microservicedockerkubernetes.msvccourses.models.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-users")
public interface UserClientFeign {
    @GetMapping("/api/user/{id}")
    public Users getUserId(@PathVariable Long id,  @RequestHeader(value = "Authorization", required = true) String token);

    @PostMapping("/api/user/save")
    public Users create(@RequestBody Users users,  @RequestHeader(value = "Authorization", required = true) String token);

    @GetMapping("/api/user/users-courses")
    public List<Users> getUsersCourses(@RequestParam Iterable<Long> ids, @RequestHeader(value = "Authorization", required = true) String token);
}
