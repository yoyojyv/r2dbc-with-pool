package me.jerry.example.r2dbcwithpool.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public Flux<User> allUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/users/by-id/{id}")
    public Mono<User> byId(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @GetMapping("/users/by-username/{username}")
    public Mono<User> byName(@PathVariable String username) {
        return this.userService.getUserByName(username);
    }

}
