package com.storage.service;

import com.storage.exceptions.NotFoundException;
import com.storage.exceptions.UserServiceException;
import com.storage.models.User;
import com.storage.models.dto.UserDto;
import com.storage.repositories.UserRepository;
import com.storage.validators.UserDtoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.storage.models.mapper.ModelMapper.fromUserDtoToUser;
import static com.storage.models.mapper.ModelMapper.fromUserToUserDto;

@Slf4j
@Service
public class UserService extends AbstractService<User>{

    public static final String USER = "User";
    public static final String ID = "id";

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(User.class, userRepository);
        this.userRepository = userRepository;
    }

    /**
     * The method that saves an user in the database
     * <p>
     * Params: UserDto.
     * Returns: <code>UserDto</code> object
     *
     * @author Pawel Konarzewski
     */
    public UserDto addUser(UserDto request) {
        log.info("Adding user with req: {} ", request);
        isRequestValid(request);
        checkEmailAvailability(request.getEmail());
        var addedUser = userRepository.save(fromUserDtoToUser(request));
        log.info("Added user");
        return fromUserToUserDto(addedUser);
    }

    /**
     * The method that validates userDto
     * <p>
     * Params: UserDto.
     * Throws: UserServiceException if userDto is not valid
     *
     * @author Pawel Konarzewski
     */
    private void isRequestValid(UserDto userDto) {
        var validator = new UserDtoValidator();
        var errors = validator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new UserServiceException("Invalid UserDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " -> " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
    }

    /**
     * The method that checks availability of email in database
     * <p>
     * Params: email.
     * Throws: UserServiceException if email exist in database
     *
     * @author Pawel Konarzewski
     */
    private void checkEmailAvailability(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceException("Invalid UserDto! Email already exist in database"));
    }

    public UserDto getUserById(Long id) {
        log.info("Enter UserService -> getUserById() with: " + id);
        var director =
                userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER, ID, id));
        return fromUserToUserDto(director);
    }
}
