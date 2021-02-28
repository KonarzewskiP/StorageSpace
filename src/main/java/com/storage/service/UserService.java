package com.storage.service;

import com.storage.exception.UserServiceException;
import com.storage.exception.ResourceNotFoundException;
import com.storage.model.dto.UserDto;
import com.storage.repository.UserRepository;
import com.storage.validator.UserDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static com.storage.constants.AppConstants.*;
import static com.storage.model.mapper.ModelMapper.fromUserDtoToUser;
import static com.storage.model.mapper.ModelMapper.fromUserToUserDto;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto addUser(UserDto userDto) {
        log.info("Enter UserRepository -> addUser() with: " + userDto);
        var validator = new UserDtoValidator();
        var errors = validator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new UserServiceException("Invalid UserDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " -> " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
        var director = fromUserDtoToUser(userDto);
        var addedDirector = userRepository.save(director);
        return fromUserToUserDto(addedDirector);
    }

    public UserDto getUserById(Long id) {
        log.info("Enter UserRepository -> getUserById() with: " + id);
        var director =
                userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(USER, ID, id));
        return fromUserToUserDto(director);
    }
}
