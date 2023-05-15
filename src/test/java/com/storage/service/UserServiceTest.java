package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.models.User;
import com.storage.models.enums.Gender;
import com.storage.models.enums.Role;
import com.storage.models.requests.CreateUserRequest;
import com.storage.repositories.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final Role ROLE = Role.ROLE_USER;
    public static final Gender GENDER = Gender.MALE;
    @InjectMocks
    private UserService underTest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class CreateTest {

        public static final String LAST_NAME = "Ford";
        public static final String FIRST_NAME = "Tom";
        public static final String EMAIL = "tom@ford.com";
        public static final String PASSWORD = "pswd";

        @Test
        void itShouldSavedNewValidUser() {
            //Given

            CreateUserRequest request = new CreateUserRequest(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE, GENDER);
            // ... return mocked user to prevent NPE
            User mockedUser = new User();
            given(userRepository.save(any())).willReturn(mockedUser);
            //When
            underTest.create(request);
            //Then
            then(userRepository).should(times(1)).save(userArgumentCaptor.capture());
            User savedUser = userArgumentCaptor.getValue();

            assertAll(
                    () -> assertThat(savedUser.getFirstName()).isEqualTo(FIRST_NAME),
                    () -> assertThat(savedUser.getLastName()).isEqualTo(LAST_NAME),
                    () -> assertThat(savedUser.getEmail()).isEqualTo(EMAIL),
                    () -> assertThat(savedUser.getRole()).isEqualTo(ROLE),
                    () -> assertThat(savedUser.getGender()).isEqualTo(GENDER),
                    () -> assertThat(savedUser.getUuid()).isNotNull()
            );
        }

        @Test
        void itShouldThrowErrorWhenTryToCreateNewUserButEmailIsAlreadyTaken() {
            //Given
            String takenEmail = "tom@ford.com";

            CreateUserRequest request = new CreateUserRequest(FIRST_NAME, LAST_NAME, takenEmail, PASSWORD, ROLE, GENDER);
            //... return true because email is already taken
            given(userRepository.existsByEmail(takenEmail)).willReturn(true);
            //When + Then
            assertThatThrownBy(() -> underTest.create(request))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining(String.format("Email [%s] is already taken!", request.email()));
        }
    }


}
