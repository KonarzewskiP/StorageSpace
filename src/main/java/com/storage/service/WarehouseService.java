package com.storage.service;

import com.storage.exceptions.AddressException;
import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.exceptions.WarehouseServiceException;
import com.storage.models.Warehouse;
import com.storage.models.dto.AddressDto;
import com.storage.models.dto.StorageRoomDto;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.dto.postcode.PostcodeDetailsManyDTO;
import com.storage.models.mapper.ModelMapper;
import com.storage.repositories.AddressRepository;
import com.storage.repositories.StorageRoomRepository;
import com.storage.repositories.WarehouseRepository;
import com.storage.validators.AddressDtoValidator;
import com.storage.validators.WarehouseDtoValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class WarehouseService extends AbstractService<Warehouse> {

    public static final String WAREHOUSE = "Warehouse";
    public static final String ID = "id";

    private final WarehouseRepository warehouseRepository;
    private final StorageRoomRepository storageRoomRepository;
    private final AddressRepository addressRepository;
    private final PostcodeService postcodeService;
    private final AddressDtoValidator addressDtoValidator;

    public WarehouseService(WarehouseRepository warehouseRepository,
                            StorageRoomRepository storageRoomRepository,
                            AddressRepository addressRepository,
                            PostcodeService postcodeService,
                            AddressDtoValidator addressDtoValidator) {
        super(Warehouse.class, warehouseRepository);
        this.warehouseRepository = warehouseRepository;
        this.storageRoomRepository = storageRoomRepository;
        this.addressRepository = addressRepository;
        this.postcodeService = postcodeService;
        this.addressDtoValidator = addressDtoValidator;
    }

    /**
     * The method add the warehouse to the database.
     * If the address is not valid, it throws an exception.
     * <p>
     *
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
     *
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
     *
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
     * The method retrieves all warehouses from the database.
     * <p>
     *
     * @return List of warehouses from the database with details.
     */
    public List<WarehouseDto> getAllWarehouses() {
        return fromWarehouseListToWarehouseDtoList(warehouseRepository.findAll());
    }

    /**
     * The method retrieves all available storage rooms to rent from the specific warehouse.
     * First retrieves the warehouse by id, if id does not exist it throws an exception. Then search
     * for all available storage rooms and map it to dto object.
     * <p>
     *
     * @param id of the warehouse to be searched for and to retrieves available storage rooms from.
     * @return List of available storage rooms with details from the specific warehouse.
     * @throws NotFoundException if the id of the warehouse does not exist.
     */

    public List<StorageRoomDto> getNotReservedStorageRoomsByWarehouseId(Long id) {
        log.info("Enter WarehouseService -> getAvailableStorageRoomsByWarehouseId() with id: " + id);
        var warehouse = findById(id);
        return warehouse.getStorageRooms()
                .stream()
                .filter(storage -> !storage.isReserved())
                .map(ModelMapper::fromStorageRoomToStorageRoomDto)
                .collect(Collectors.toList());
    }

    /**
     * The method that sort all warehouses from the database in ascending order by distance from
     * the postcode given by user in call to the controller method- getNearestWarehouses().
     *
     * @param postcode postcode given by the user.
     * @return list of ordered warehouses in descending order. From the closest to the most further away,
     * according to the postcode given by the user.
     */
    public List<WarehouseDto> getOrderedByDistanceFromPostcode(String postcode) {
        if (StringUtils.isBlank(postcode))
            throw new BadRequestException("Postcode can not be null or empty");


        var warehousesList = warehouseRepository.findAll();
        var listOfPostcodesFromEachWarehouse = warehousesList
                .stream()
                .map(warehouse -> warehouse.getAddress().getPostcode())
                .collect(Collectors.toList());

        PostcodeDetailsManyDTO coordinatesOfWarehouses = postcodeService.getMultipleCoordinates(listOfPostcodesFromEachWarehouse);
        var userPostcodeCoordinates = postcodeService.getSingleCoordinates(postcode);

//        var map = getWarehousePostcodeByDistanceFromUserPostcode(userPostcodeCoordinates, coordinatesOfWarehouses);
//        var sortedMap = sortPostcodesByDistance(map);
//        var orderedList = getOrderedListOfWarehouses(warehousesList, sortedMap);
//        return ModelMapper.fromWarehouseListToWarehouseDtoList(orderedList);
        return null;
    }

}
