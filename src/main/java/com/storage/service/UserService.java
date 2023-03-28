package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.UserServiceException;
import com.storage.models.User;
import com.storage.models.requests.CreateUserRequest;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.repositories.UserRepository;
import com.storage.utils.UuidGenerator;
import com.storage.validators.UserDtoValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.storage.utils.mapper.ModelMapper.fromUserDtoToUser;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Service
public class UserService extends AbstractService<User> {

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
    public User createUser(CreateUserRequest request) {
        log.info("Creating new user with req: {} ", request);
        isRequestValid(request);

        if (isEmailTaken(request.getEmail()))
            throw new BadRequestException(String.format("Email [%s] is already taken!", request.getEmail()));

        var newUser = userRepository.save(fromUserDtoToUser(request));
        log.info("Created new user with UUID [{}] and name: [{}]", newUser.getUuid(), getFullName(newUser));
        return newUser;
    }

    /**
     * The method that checks availability of email in database
     * <p>
     * Params: email.
     * Throws: UserServiceException if email exist in database
     *
     * @author Pawel Konarzewski
     */
    private boolean isEmailTaken(String email) {
        if (isBlank(email))
            throw new BadRequestException("Email can not be null or empty!");

        return userRepository.existsByEmail(email);
    }


    public String getFullName(User user) {
        if (user == null)
            throw new BadRequestException("User can not be null!");
        return getFullName(user.getFirstName(), user.getLastName());
    }

    public String getFullName(String firstName, String lastName) {
        if (isBlank(firstName) && isBlank(lastName))
            return "";
        if (isBlank(firstName))
            return lastName;
        if (isBlank(lastName))
            return firstName;
        return firstName + " " + lastName;
    }

    /**
     * The method that validates userDto
     * <p>
     * Params: UserDto.
     * Throws: UserServiceException if userDto is not valid
     *
     * @author Pawel Konarzewski
     */

    private void isRequestValid(CreateUserRequest createUserRequest) {
        var validator = new UserDtoValidator();
        var errors = validator.validate(createUserRequest);
        if (!errors.isEmpty()) {
            throw new UserServiceException("Invalid UserDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " -> " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
    }


    @Transactional
    public User saveNewCustomer(QuoteEstimateRequest request) {
        User newUser = User.builder()
                .uuid(UuidGenerator.next())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        return userRepository.save(newUser);
    }
}















