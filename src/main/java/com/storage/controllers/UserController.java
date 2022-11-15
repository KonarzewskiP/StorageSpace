package com.storage.controllers;

import com.storage.models.dto.UserDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        log.info("Enter UserController -> addUser() with: " + userDto);
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getByUuid(@PathVariable String uuid) {
        var user = userService.findByUuid(uuid);
        var userDTO = ModelMapper.fromUserToUserDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}

