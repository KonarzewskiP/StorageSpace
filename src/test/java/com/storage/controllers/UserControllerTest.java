package com.storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.models.User;
import com.storage.models.requests.CreateUserRequest;
import com.storage.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.storage.models.enums.Gender.MALE;
import static com.storage.models.enums.Role.CUSTOMER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    private static final String FIRST_NAME = "Slow";
    private static final String LAST_NAME = "Fast";
    private static final String EMAIL = "slow@fast.com";
    private static final String USER_UUID = "user-uuid";

    @Nested
    class CreateTest {
        @Test
        void itShouldCreateANewUser() throws Exception {
            //Given
            CreateUserRequest createUserRequest = CreateUserRequest.builder().build();
            //... return new user
            User user = User.builder()
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .email(EMAIL)
                    .role(CUSTOMER)
                    .gender(MALE)
                    .build();
            given(userService.create(createUserRequest)).willReturn(user);
            //When
            ResultActions result = mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(toJson(createUserRequest)));
            //Then
            result.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                    .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                    .andExpect(jsonPath("$.email").value(EMAIL))
                    .andExpect(jsonPath("$.role").value(CUSTOMER.name()))
                    .andExpect(jsonPath("$.gender").value(MALE.name()));
        }
    }

    @Nested
    class GetByUuidTest {
        @Test
        void itShouldGetByUuid() throws Exception {
            //Given
            User user = User.builder()
                    .firstName(FIRST_NAME)
                    .lastName(LAST_NAME)
                    .email(EMAIL)
                    .role(CUSTOMER)
                    .gender(MALE)
                    .build();
            given(userService.findByUuid(USER_UUID)).willReturn(user);

            //When
            ResultActions result = mockMvc.perform(get("/users/{uuid}", USER_UUID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE));
            //Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                    .andExpect(jsonPath("$.lastName").value(LAST_NAME))
                    .andExpect(jsonPath("$.email").value(EMAIL))
                    .andExpect(jsonPath("$.role").value(CUSTOMER.name()))
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