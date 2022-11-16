package com.storage.controllers;

import com.storage.models.mapper.ModelMapper;
import com.storage.models.requests.createUserRequest;
import com.storage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.storage.models.mapper.ModelMapper.fromUserToUserDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<createUserRequest> create(@RequestBody createUserRequest createUserRequest) {
        var newUser = userService.createUser(createUserRequest);
        var userDto = fromUserToUserDto(newUser);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<createUserRequest> getByUuid(@PathVariable String uuid) {
        var user = userService.findByUuid(uuid);
        var userDTO = ModelMapper.fromUserToUserDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}

