package com.storage.service;

import com.storage.exceptions.NotFoundException;
import com.storage.exceptions.UserServiceException;
import com.storage.models.User;
import com.storage.models.dto.UserDto;
import com.storage.models.enums.Gender;
import com.storage.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.storage.builders.Fixtures.createUser;
import static com.storage.builders.Fixtures.createUserDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        var result = service.addUser(testUserDto);
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
        UserDto userDto = null;
        //when
        Throwable thrown = catchThrowable(() -> service.addUser(userDto));
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
        Throwable thrown = catchThrowable(() -> service.addUser(userDto));
        //then
        assertThat(thrown)
                .isInstanceOf(UserServiceException.class)
                .hasMessageContaining("Invalid UserDto!")
                .hasMessageContaining("Should start from uppercase")
                .hasMessageContaining("Can not be empty");
    }

    @Test
    @DisplayName("should find user by id")
    void shouldFindUserById() {
        //given
        var user = createUser();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //when
        var result = service.getUserById(10L);
        //then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when can not find user by id")
    void shouldThrowResourceNotFoundExceptionWhenCanNotFindUserById() {
        //given
        var id = 999L;
        //when
        Throwable thrown = catchThrowable(() -> service.getUserById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User not found with id: 999");
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when id is null")
    void shouldThrowResourceNotFoundExceptionWhenIdIsNull() {
        //given
        Long id = null;
        //when
        Throwable thrown = catchThrowable(() -> service.getUserById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User not found with id: null");
    }
}
