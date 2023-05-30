package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.User;
import com.storage.models.requests.CreateUserRequest;
import com.storage.repositories.UserRepository;
import com.storage.security.config.SecurityConfig;
import com.storage.security.tokens.TokensService;
import com.storage.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.storage.models.enums.Gender.MALE;
import static com.storage.models.enums.Role.ROLE_USER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@Import(value = {SecurityConfig.class, TokensService.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    private static final String FIRST_NAME = "Slow";
    private static final String LAST_NAME = "Fast";
    private static final String EMAIL = "slow@fast.com";
    private static final String USER_UUID = "user-uuid";
    private static final String PASSWORD = "password";

    @Nested
    class CreateTest {
        @Test
//        @WithMockUser
        void itShouldCreateANewUser() throws Exception {
            //Given
            CreateUserRequest createUserRequest = CreateUserRequest.builder().build();
            //... return new user
            User user = User.builder()
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .role(ROLE_USER)
                    .gender(MALE)
                    .build();
            given(userService.create(createUserRequest)).willReturn(user);
            //When
            ResultActions result = mockMvc.perform(post("/api/v1/users/register")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(toJson(createUserRequest)));
            //Then
            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                    .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                    .andExpect(jsonPath("$.email").value(EMAIL))
                    .andExpect(jsonPath("$.role").value(ROLE_USER.name()))
                    .andExpect(jsonPath("$.gender").value(MALE.name()));
        }
    }

    @Nested
    class GetByUuidTest {
        @Test
        @WithMockUser
        void itShouldGetByUuid() throws Exception {
            //Given
            User user = User.builder()
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .email(EMAIL)
                    .role(ROLE_USER)
                    .gender(MALE)
                    .build();
            given(userService.findByUuid(USER_UUID)).willReturn(user);

            //When
            ResultActions result = mockMvc.perform(get("/api/v1/users/{uuid}", USER_UUID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                    .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                    .andExpect(jsonPath("$.email").value(EMAIL))
                    .andExpect(jsonPath("$.role").value(ROLE_USER.name()))
                    .andExpect(jsonPath("$.gender").value(MALE.name()));
        }
    }

    private <T> String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}