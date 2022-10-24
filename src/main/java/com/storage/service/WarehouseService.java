package com.storage.service;

import com.storage.exceptions.AddressException;
import com.storage.exceptions.ResourceNotFoundException;
import com.storage.exceptions.WarehouseServiceException;
import com.storage.models.dto.AddressDto;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.repositories.AddressRepository;
import com.storage.repositories.StorageRoomRepository;
import com.storage.repositories.WarehouseRepository;
import com.storage.service.postcodes_api.PostcodeService;
import com.storage.validators.AddressDtoValidator;
import com.storage.validators.WarehouseDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.storage.models.mapper.ModelMapper.*;
import static com.storage.utils.Util.createStorageRoomsList;

/**
 * @author Pawel Konarzewski
 * @version 1.0
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {

    public static final String WAREHOUSE = "Warehouse";
    public static final String ID = "id";

    private final WarehouseRepository warehouseRepository;
    private final StorageRoomRepository storageRoomRepository;
    private final AddressRepository addressRepository;
    private final PostcodeService postcodeService;
    private final AddressDtoValidator addressDtoValidator;

    /**
     * The method add the warehouse to the database.
     * If the address is not valid, it throws an exception.
     * <p>
     * @param warehouseDto object to be saved.
     * @return Saved warehouse to the database as dto object.
     */

    public WarehouseDto addWarehouse(WarehouseDto warehouseDto) {
        log.info("Enter WarehouseService -> addWarehouse() with: " + warehouseDto);
        isWarehouseDtoValid(warehouseDto);
        isAddressDtoValid(warehouseDto.getAddress());
        var warehouse = fromWarehouseDtoToWarehouse(warehouseDto);
        var address = fromAddressDtoToAddress(warehouseDto.getAddress());
        addressRepository.save(address);
        var list = createStorageRoomsList();
        storageRoomRepository.saveAll(list);
        warehouse.setStorageRooms(list);
        warehouse.setAddress(address);
        var addedWarehouse = warehouseRepository.save(warehouse);
        return fromWarehouseToWarehouseDto(addedWarehouse);
    }

    /**
     * The method checks if the address passed inside WarehouseDto is in a valid format.
     * If the address is not valid, it throws an exception.
     * <p>
     * @param addressDto object to be saved.
     * @throws AddressException if the address details are not valid.
     */

    private void isAddressDtoValid(AddressDto addressDto) {
        var errors = addressDtoValidator.validate(addressDto);
        if (!errors.isEmpty()) {
            throw new AddressException("Invalid AddressDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(",")));
        }
    }

    /**
     * The method checks if the warehouseDto is valid.
     * If warehouseDto is not valid, it throws an exception.
     * <p>
     * @param warehouseDto object to be saved.
     * @throws WarehouseServiceException if the warehouse details are not valid.
     */

    private void isWarehouseDtoValid(WarehouseDto warehouseDto) {
        var validator = new WarehouseDtoValidator();
        var errors = validator.validate(warehouseDto);
        if (!errors.isEmpty()) {
            throw new WarehouseServiceException("Invalid WarehouseDto!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(",")));
        }
    }


    /**
     * The method retrieves the warehouse by id from the database.
     * <p>
     * @param id of the warehouse to be searched for.
     * @throws ResourceNotFoundException if the id of the warehouse does not exist.
     * @return WarehouseDto with details about the specific warehouse.
     */

    public WarehouseDto getWarehouseById(Long id) {
        log.info("Enter WarehouseService -> addWarehouse() with id: " + id);
        var warehouse = warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WAREHOUSE, ID, id));
        return fromWarehouseToWarehouseDto(warehouse);
    }

    /**
     * The method retrieves all warehouses from the database.
     * <p>
     * @return List of warehouses from the database with details.
     */
    public List<WarehouseDto> getAllWarehouses() {
        log.info("Enter WarehouseService -> getAllWarehouses()");
        return fromWarehouseListToWarehouseDtoList(warehouseRepository.findAll());
    }

    /**
     * The method retrieves all available storage rooms to rent from the specific warehouse.
     * First retrieves the warehouse by id, if id does not exist it throws an exception. Then search
     * for all available storage rooms and map it to dto object.
     * <p>
     * @param id of the warehouse to be searched for and to retrieves available storage rooms from.
     * @throws ResourceNotFoundException if the id of the warehouse does not exist.
     * @return List of available storage rooms with details from the specific warehouse.
     */

    public List<StorageRoomDto> getNotReservedStorageRoomsByWarehouseId(Long id) {
        log.info("Enter WarehouseService -> getAvailableStorageRoomsByWarehouseId() with id: " + id);
        var warehouse = warehouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WAREHOUSE, ID, id));
        return warehouse.getStorageRooms()
                .stream()
                .filter(storage -> !storage.isReserved())
                .map(ModelMapper::fromStorageRoomToStorageRoomDto)
                .collect(Collectors.toList());
    }
}
