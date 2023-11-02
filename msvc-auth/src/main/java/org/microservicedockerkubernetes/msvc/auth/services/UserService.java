package org.microservicedockerkubernetes.msvc.auth.services;

import org.microservicedockerkubernetes.msvc.auth.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private WebClient.Builder webClient;

    private Logger log = LoggerFactory.getLogger(UserService.class);
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Users users = webClient
                    .build()
                    .get()
                    .uri("http://msvc-users/login", uri -> uri.queryParam("email", email).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Users.class)
                    .block();
            log.info("User login: " + users.getEmail());
            log.info("User login: " + users.getName());
            return new User(email, users.getPassword(), true, true, true, true, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (RuntimeException e) {
            String error = "Error, no existe el usuario en el sistema" + email;
            log.error(error);
            log.error(e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }
}
