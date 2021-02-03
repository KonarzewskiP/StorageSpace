package com.storage.service;

import com.storage.model.Director;
import com.storage.model.ModelMapper;
import com.storage.model.dto.DirectorDto;
import com.storage.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorDto addDirector(DirectorDto directorDto) {
        try {
            Optional.ofNullable(directorDto).orElseThrow(()-> new IllegalArgumentException("DirectorDTO is null"));
            Director director = ModelMapper.fromDirectorDtoToDirector(directorDto);

            directorRepository.save(director);
            return ModelMapper.fromDirectorToDirectorDto(director);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to add new director");
        }
    }

    public DirectorDto getOneDirectorById(Long id) {
        try {
            Optional.ofNullable(id).orElseThrow(()-> new IllegalArgumentException("Id is null"));
            Director director = directorRepository.findById(id).orElseThrow(IllegalStateException::new);
            return ModelMapper.fromDirectorToDirectorDto(director);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to get director by id: " + id);
        }
    }
}
