package com.storage.service;

import com.storage.exceptions.UserServiceException;
import com.storage.exceptions.ResourceNotFoundException;
import com.storage.models.dto.UserDto;
import com.storage.repositories.UserRepository;
import com.storage.validators.UserDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static com.storage.constants.AppConstants.*;
import static com.storage.models.mapper.ModelMapper.fromUserDtoToUser;
import static com.storage.models.mapper.ModelMapper.fromUserToUserDto;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto addUser(UserDto userDto) {
        log.info("Enter UserService -> addUser() with: " + userDto);
        var validator = new UserDtoValidator();
        var errors = validator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new UserServiceException("Invalid UserDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " -> " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
        checkEmailAvailability(userDto.getEmail());
        var addedUser = userRepository.save(fromUserDtoToUser(userDto));
        return fromUserToUserDto(addedUser);
    }

    private void checkEmailAvailability(String email) {
        var user = userRepository.findByEmail(email).isPresent();
        if (user) {
            throw new UserServiceException("Invalid UserDto! Email already exist in database");
        }
    }

    public UserDto getUserById(Long id) {
        log.info("Enter UserService -> getUserById() with: " + id);
        var director =
                userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(USER, ID, id));
        return fromUserToUserDto(director);
    }
}
