package com.project.webflux.controller;

import com.project.webflux.dto.UserDto;
import com.project.webflux.mapper.UserMapper;
import com.project.webflux.model.User;
import com.project.webflux.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Api(value = "WebFlux API user")
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @ApiOperation(value = "Return all users", response = UserDto.class)
    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        return userService.findAll();
    }

    @ApiOperation(value = "Create user", response = User.class)
    @PostMapping("/users")
    public Mono<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);

        return userService.createUser(user).map(u -> userMapper.userToUserDto(u));
    }

    @ApiOperation(value = "Find user by id", response = UserDto.class)
    @GetMapping("/users/{id}")
    public Mono<ResponseEntity<UserDto>> findById(@PathVariable(value = "id") String userId) {
        return userService.findById(userId)
                .map(savedUser -> ResponseEntity.ok(
                        userMapper.userToUserDto(savedUser)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update user by id", response = UserDto.class)
    @PutMapping("/users/{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable(value = "id") String userId,
                                                    @Valid @RequestBody UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        return userService.update(user)
                .map(updateUser -> new ResponseEntity<>(
                        userMapper.userToUserDto(updateUser), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Delete user by id")
    @DeleteMapping("/users/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable(value = "id") String userId) {

        return userService.delete(userId)
                .map(delete -> new ResponseEntity<Void>(HttpStatus.NOT_FOUND))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.OK));
    }

    @ApiOperation(value = "Get all users stream", response = UserDto.class)
    @GetMapping(value = "/stream/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDto> streamAllUsers() {
        return userService.findAll().map(u -> userMapper.userToUserDto(u));
    }

}
