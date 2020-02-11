package com.project.webflux.service;

import com.project.webflux.model.User;
import com.project.webflux.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {

    Mono<User> createUser(User user);

    Flux<User> findAll();

    Mono<User> findById(String userId);

    Mono<User> update(User user);

    Mono<Void> delete(String userId);

}
