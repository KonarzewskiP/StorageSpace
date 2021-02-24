package com.storage.service;

import com.storage.model.Director;
import com.storage.model.mapper.ModelMapper;
import com.storage.model.Warehouse;
import com.storage.model.dto.DirectorDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.repository.DirectorRepository;
import com.storage.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final DirectorRepository directorRepository;

    public WarehouseDto addWarehouse(WarehouseDto warehouseDto) {
        try {
            Optional.ofNullable(warehouseDto).orElseThrow(() -> new IllegalArgumentException("WarehouseDto is null"));
            Warehouse warehouse = ModelMapper.fromWarehouseDtoToWarehouse(warehouseDto);
            return ModelMapper.fromWarehouseToWarehouseDto(warehouseRepository.save(warehouse));
        } catch (Exception e) {
            throw new IllegalStateException("Fail to add new warehouse.");
        }
    }

    public WarehouseDto getOneWarehouseById(Long id) {
        try {
            Optional.ofNullable(id).orElseThrow(() -> new IllegalArgumentException("Id is null"));
            Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            return ModelMapper.fromWarehouseToWarehouseDto(warehouse);
        } catch (Exception e) {
            throw new IllegalStateException("Fail to get warehouse by id: " + id);
        }
    }

    public WarehouseDto updateWarehouseDirector(DirectorDto directorDto, Long id) {
        try {
            Optional.ofNullable(directorDto).orElseThrow(() -> new IllegalArgumentException("DirectorDto is null"));
            Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new IllegalStateException("Fail to get warehouse by id: " + id));
            Director director = directorRepository.findById(directorDto.getId()).orElseThrow(() -> new IllegalStateException("Fail to get director by id: " + directorDto.getId()));
            log.info("Enter WarehouseService -> updateWarehouseDirector() with warehouse: " + warehouse);
            log.info("Enter WarehouseService -> updateWarehouseDirector() with director: " + director);
            director.getWarehouses().add(warehouse);
            warehouse.setDirector(director);
            return ModelMapper.fromWarehouseToWarehouseDto(warehouse);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
