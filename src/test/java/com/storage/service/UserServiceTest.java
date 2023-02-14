package com.storage.service;

import com.storage.exceptions.UserServiceException;
import com.storage.models.User;
import com.storage.models.enums.Gender;
import com.storage.models.requests.CreateUserRequest;
import com.storage.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.storage.builders.Fixtures.createUser;
import static com.storage.builders.Fixtures.createUserDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("should add user to repository")
    void shouldAddUserToRepository() {
        //given
        var user = createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        var testUserDto = createUserDto();
        //when
        var result = service.createUser(testUserDto);
        //then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getFirstName()).isEqualTo("Veronica"),
                () -> assertThat(result.getLastName()).isEqualTo("Jobs"),
                () -> assertThat(result.getGender()).isEqualTo(Gender.FEMALE)
        );
    }

    @Test
    @DisplayName("should throw UserServiceException when UserDto is null")
    void shouldThrowUserServiceExceptionWhenUserDtoIsNull() {
        //given
        CreateUserRequest createUserRequest = null;
        //when
        Throwable thrown = catchThrowable(() -> service.createUser(createUserRequest));
        //then
        assertThat(thrown)
                .isInstanceOf(UserServiceException.class)
                .hasMessageContaining("Invalid UserDto!");
    }

    @Test
    @DisplayName("should throw UserServiceException when UserDto is invalid")
    void shouldThrowUserServiceExceptionWhenUserDtoIsInvalid() {
        //given
        var userDto = createUserDto();
        userDto.setFirstName("roger");
        userDto.setLastName("");
        //when
        Throwable thrown = catchThrowable(() -> service.createUser(userDto));
        //then
        assertThat(thrown)
                .isInstanceOf(UserServiceException.class)
                .hasMessageContaining("Invalid UserDto!")
                .hasMessageContaining("Should start from uppercase")
                .hasMessageContaining("Can not be empty");
    }
}
