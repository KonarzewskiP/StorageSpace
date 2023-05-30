package com.storage.controllers;

import com.storage.models.User;
import com.storage.models.dto.UserDTO;
import com.storage.models.requests.CreateUserRequest;
import com.storage.service.UserService;
import com.storage.utils.mapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.storage.utils.mapper.ModelMapper.fromUserToUserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserRequest createUserRequest) {
        User newUser = userService.create(createUserRequest);
        UserDTO userDto = fromUserToUserDto(newUser);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> getByUuid(@PathVariable String uuid) {
        User user = userService.findByUuid(uuid);
        UserDTO userDTO = ModelMapper.fromUserToUserDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}

