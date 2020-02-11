package com.project.webflux;

import com.project.webflux.dto.UserDto;
import com.project.webflux.model.User;
import com.project.webflux.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class UserControllerTest extends WebfluxApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    UserRepository userRepository;

    private final static String FIRST_NAME = "Jackson";


    private final static String LAST_NAME = "Paiva";

    @Test
    public void testCreateUser() {
        User user = User.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), UserDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo(FIRST_NAME);
    }

    @Test
    public void testGetAllUsers() {
        webTestClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(UserDto.class);
    }

    @Test
    public void testFindUserById() {
        User userSave = User.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();

        User user = userRepository.save(userSave).block();

        webTestClient.get()
                .uri("/users/{id}", Collections.singletonMap("id", user.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdateUser() {
        User userSave = User.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();

        User user = userRepository.save(userSave).block();
        User userUpdate = user;
        userUpdate.setLastName(LAST_NAME + "S");

        webTestClient.put()
                .uri("/users/{id}", Collections.singletonMap("id", user.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userUpdate), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.lastName").isEqualTo(LAST_NAME + "S");
    }

    @Test
    public void testUserDelete() {
        User user = User.builder().firstName(FIRST_NAME).lastName(LAST_NAME).build();

        User userSave = userRepository.save(user).block();

        webTestClient.delete()
                .uri("/users/{id}", Collections.singletonMap("id", userSave.getId()))
                .exchange()
                .expectStatus().isOk();
    }
}
