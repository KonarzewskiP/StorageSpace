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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService underTest;
    @Mock
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class CreateTest {

        @Test
        void itShouldSavedNewValidUser() {
            //Given
            String firstName = "Tom";
            String lastName = "Ford";
            String email = "tom@ford.com";
            Role role = Role.CUSTOMER;
            Gender gender = Gender.MALE;

            CreateUserRequest request = new CreateUserRequest(firstName, lastName, email, role, gender);
            // ... return mocked user to prevent NPE
            User mockedUser = new User();
            given(userRepository.save(any())).willReturn(mockedUser);
            //When
            underTest.create(request);
            //Then
            then(userRepository).should(times(1)).save(userArgumentCaptor.capture());
            User savedUser = userArgumentCaptor.getValue();

            assertAll(
                    () -> assertThat(savedUser.getFirstName()).isEqualTo(firstName),
                    () -> assertThat(savedUser.getLastName()).isEqualTo(lastName),
                    () -> assertThat(savedUser.getEmail()).isEqualTo(email),
                    () -> assertThat(savedUser.getRole()).isEqualTo(role),
                    () -> assertThat(savedUser.getGender()).isEqualTo(gender),
                    () -> assertThat(savedUser.getUuid()).isNotNull()
            );
        }

        @Test
        void itShouldThrowErrorWhenTryToCreateNewUserButEmailIsAlreadyTaken() {
            //Given
            String firstName = "Tom";
            String lastName = "Ford";
            String email = "tom@ford.com";
            Role role = Role.CUSTOMER;
            Gender gender = Gender.MALE;

            CreateUserRequest request = new CreateUserRequest(firstName, lastName, email, role, gender);

            given(userRepository.existsByEmail(email)).willReturn(true);
            //When + Then
            assertThatThrownBy(() -> underTest.create(request))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining(String.format("Email [%s] is already taken!", request.email()));
        }
    }


}
