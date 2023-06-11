package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.User;
import com.storage.models.dto.ActivationEmailDTO;
import com.storage.models.requests.CreateUserRequest;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.repositories.UserRepository;
import com.storage.repositories.VerificationTokenRepository;
import com.storage.utils.StringUtils;
import com.storage.utils.UuidGenerator;
import com.storage.validators.UserDtoValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Service
public class UserService extends AbstractService<User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       VerificationTokenRepository verificationTokenRepository) {
        super(User.class, userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    /**
     * The method that saves an user in the database
     * <p>
     * Params: UserDto.
     * Returns: <code>UserDto</code> object
     *
     * @author Pawel Konarzewski
     */
    public User create(CreateUserRequest request) {
        log.info("Creating new user with req: {} ", request);
        UserDtoValidator.validate(request);

        if (isEmailTaken(request.email()))
            throw new BadRequestException(String.format("Email [%s] is already taken!", request.email()));


        User user = User.builder()
                .uuid(UuidGenerator.next())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .role(request.role())
                .gender(request.gender())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User newUser = userRepository.save(user);
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
    public boolean isEmailTaken(String email) {
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

    @Transactional
    public User saveNewCustomer(QuoteEstimateRequest request) {
        User newUser = User.builder()
                .uuid(UuidGenerator.next())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        return userRepository.save(newUser);
    }

    public User findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new BadRequestException("Email can not be empty or null");
        }

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User not found by email: " + email));
    }

    public ActivationEmailDTO activateEmailAccount(final String token) {
        if (StringUtils.isBlank(token)) {
            throw new BadRequestException("Activation token is invalid");
        }

        Long userId = verificationTokenRepository.findUserIdByToken(token)
                .orElseThrow(() -> new NotFoundException("User not found by verification token: " + token));

        userRepository.activateAccount(userId);
        return new ActivationEmailDTO(true);
    }
}















