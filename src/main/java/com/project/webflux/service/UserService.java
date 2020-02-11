package com.project.webflux.service;

import com.project.webflux.model.User;
import com.project.webflux.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository userRepository;

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> update(User user) {
        return userRepository.save(user);
    }

    public Mono<Void> delete(String userId) {
        return userRepository.deleteById(userId);
    }
}
