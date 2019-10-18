package me.jerry.example.r2dbcwithpool.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true, transactionManager = "userDbTransactionManager")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Flux<User> getAllUsers() {
        return repository.findAll();
    }

    public Mono<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public Mono<User> getUserByName(String username) {
        return repository.findByUsername(username);
    }

}
