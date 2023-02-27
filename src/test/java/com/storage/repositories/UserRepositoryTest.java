package com.storage.repositories;

import com.storage.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository underTest;


    @Test
    void itShouldFinsByUuid() {
        //Given
        String uuid = UUID.randomUUID().toString();
        String name = "Tony";
        String lastName = "Hawk";
        String email = "tonny@hawk.com";
        User user = User.builder()
                .uuid(uuid)
                .firstName(name)
                .lastName(lastName)
                .email(email)
                .build();

        entityManager.persist(user);
        //When
        var result = underTest.findByUuid(uuid);
        //Then
        assertThat(result)
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u).usingRecursiveComparison().isEqualTo(user);
                });
    }
}