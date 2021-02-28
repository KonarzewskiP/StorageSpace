package com.storage.service;

import com.storage.exception.DirectorServiceException;
import com.storage.exception.ResourceNotFoundException;
import com.storage.model.Director;
import com.storage.model.dto.DirectorDto;
import com.storage.model.enums.Gender;
import com.storage.repository.DirectorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.storage.builders.MockDataForTest.createDirector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DirectorServiceTest {

    @InjectMocks
    private DirectorService service;

    @Mock
    private DirectorRepository directorRepository;

    @Test
    @DisplayName("should add director to repository")
    void shouldAddDirectorToRepository() {
        //given
        var director = createDirector();
        when(directorRepository.save(any(Director.class))).thenReturn(director);
        var testDirectorDto = DirectorDto.builder()
                .firstName("Frodo")
                .lastName("Baggins")
                .gender(Gender.MALE)
                .build();
        //when
        var result = service.addDirector(testDirectorDto);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Veronica");
        assertThat(result.getLastName()).isEqualTo("Jobs");
        assertThat(result.getGender()).isEqualTo(Gender.FEMALE);
    }

    @Test
    @DisplayName("should throw DirectorServiceException when DirectorDto is null")
    void shouldThrowDirectorServiceExceptionWhenDirectorDtoIsNull() {
        //given
        DirectorDto directorDto = null;
        //when
        Throwable thrown = catchThrowable(() -> service.addDirector(directorDto));
        //then
        assertThat(thrown)
                .isInstanceOf(DirectorServiceException.class)
                .hasMessageContaining("Invalid DirectorDto!");
    }

    @Test
    @DisplayName("should throw DirectorServiceException when DirectorDto is invalid")
    void shouldThrowDirectorServiceExceptionWhenDirectorDtoIsInvalid() {
        //given
        var directorDto = DirectorDto.builder()
                .firstName("roger")
                .lastName("")
                .gender(Gender.MALE)
                .build();
        //when
        Throwable thrown = catchThrowable(() -> service.addDirector(directorDto));
        //then
        assertThat(thrown)
                .isInstanceOf(DirectorServiceException.class)
                .hasMessageContaining("Invalid DirectorDto!")
                .hasMessageContaining("Should start from uppercase")
                .hasMessageContaining("Can not be empty");
    }

    @Test
    @DisplayName("should find director by id")
    void shouldFindDirectorById() {
        //given
        var director = createDirector();
        when(directorRepository.findById(anyLong())).thenReturn(Optional.of(director));
        //when
        var result = service.getDirectorById(10L);
        //then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when can not find director by id")
    void shouldThrowResourceNotFoundExceptionWhenCanNotFindDirectorById() {
        //given
        var id = 999L;
        //when
        Throwable thrown = catchThrowable(() ->  service.getDirectorById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Director not found with id: 999");
    }

    @Test
    @DisplayName("should throw ResourceNotFoundException when id is null")
    void shouldThrowResourceNotFoundExceptionWhenIdIsNull() {
        //given
        Long id = null;
        //when
        Throwable thrown = catchThrowable(() ->  service.getDirectorById(id));
        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Director not found with id: null");
    }
}
