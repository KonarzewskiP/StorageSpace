package com.storage.service;

import com.storage.exception.DirectorServiceException;
import com.storage.exception.ResourceNotFoundException;
import com.storage.model.Director;
import com.storage.model.mapper.ModelMapper;
import com.storage.model.dto.DirectorDto;
import com.storage.repository.DirectorRepository;
import com.storage.validator.DirectorDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static com.storage.constants.AppConstants.*;
import static com.storage.model.mapper.ModelMapper.fromDirectorDtoToDirector;
import static com.storage.model.mapper.ModelMapper.fromDirectorToDirectorDto;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorDto addDirector(DirectorDto directorDto) {
        log.info("Enter DirectorRepository -> addDirector() with: " + directorDto);
        var validator = new DirectorDtoValidator();
        var errors = validator.validate(directorDto);
        if (!errors.isEmpty()) {
            throw new DirectorServiceException("Invalid DirectorDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " -> " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
        var director = fromDirectorDtoToDirector(directorDto);
        var addedDirector = directorRepository.save(director);
        return fromDirectorToDirectorDto(addedDirector);
    }

    public DirectorDto getDirectorById(Long id) {
        log.info("Enter DirectorRepository -> getOneDirectorById() with: " + id);
        var director =
                directorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(DIRECTOR, ID, id));
        return fromDirectorToDirectorDto(director);
    }
}
