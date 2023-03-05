package com.storage.service;

import com.storage.exceptions.WarehouseServiceException;
import com.storage.models.Warehouse;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.mapper.ModelMapper;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.repositories.StorageRoomRepository;
import com.storage.repositories.WarehouseRepository;
import com.storage.validators.WarehouseCreateRequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.storage.models.mapper.ModelMapper.fromWarehouseToWarehouseDto;
import static com.storage.utils.Util.createStorageRoomsList;

/**
 * @author Pawel Konarzewski
 * @version 1.0
 */

@Slf4j
@Service
@Transactional
public class WarehouseService extends AbstractService<Warehouse> {

    private final WarehouseRepository warehouseRepository;
    private final StorageRoomRepository storageRoomRepository;
    private final PostcodeService postcodeService;

    public WarehouseService(WarehouseRepository warehouseRepository,
                            StorageRoomRepository storageRoomRepository,
                            PostcodeService postcodeService
    ) {
        super(Warehouse.class, warehouseRepository);
        this.warehouseRepository = warehouseRepository;
        this.storageRoomRepository = storageRoomRepository;
        this.postcodeService = postcodeService;
    }

    /**
     * The method add the warehouse to the database.
     * If the address is not valid, it throws an exception.
     * <p>
     *
     * @param warehouseRequest object to be saved.
     * @return Saved warehouse to the database as dto object.
     */

    public WarehouseDto addWarehouse(CreateWarehouseRequest warehouseRequest) {
        isCreateRequestValid(warehouseRequest);
        //TODO call postcode service and get coordinates of the postcode
        // Coordinates coordinates = postcodeService.getCoordinates(warehouseRequest.getPostcode());
        var warehouse = createNew(warehouseRequest);
        var list = createStorageRoomsList();
        storageRoomRepository.saveAll(list);
//        warehouse.setStorageRooms(list);
//        warehouse.setAddress(address);
        var addedWarehouse = warehouseRepository.save(warehouse);
        return fromWarehouseToWarehouseDto(addedWarehouse);
    }

    private Warehouse createNew(CreateWarehouseRequest req) {
        return Warehouse.builder()
                .name(req.getName())
                .city(req.getCity())
                .postcode(req.getPostcode())
                .build();
    }

    /**
     * The method checks if the Create Request is valid.
     * If it is not valid, it throws an exception.
     * <p>
     *
     * @param warehouseRequest request used to create a new Warehouse object.
     * @throws WarehouseServiceException if the warehouse details are not valid.
     */

    private void isCreateRequestValid(CreateWarehouseRequest warehouseRequest) {
        var validator = new WarehouseCreateRequestValidator();
        var errors = validator.validate(warehouseRequest);
        if (!errors.isEmpty())
            validator.throwException(errors);
    }

    /**
     * The method retrieves all warehouses from the database.
     * <p>
     *
     * @return List of warehouses from the database with details.
     */
    public Page<WarehouseDto> getAll(Pageable pageable) {
        return warehouseRepository.findAll(pageable).map(ModelMapper::fromWarehouseToWarehouseDto);
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
//        if (StringUtils.isBlank(postcode))
//            throw new BadRequestException("Postcode can not be null or empty");
//
//
//        var warehousesList = warehouseRepository.findAll();
//        var listOfPostcodesFromEachWarehouse = warehousesList
//                .stream()
//                .map(warehouse -> warehouse.getAddress().getPostcode())
//                .collect(Collectors.toList());
//
//        PostcodeDetailsManyDTO coordinatesOfWarehouses = postcodeService.getMultipleCoordinates(listOfPostcodesFromEachWarehouse);
//        var userPostcodeCoordinates = postcodeService.getSingleCoordinates(postcode);

//        var map = getWarehousePostcodeByDistanceFromUserPostcode(userPostcodeCoordinates, coordinatesOfWarehouses);
//        var sortedMap = sortPostcodesByDistance(map);
//        var orderedList = getOrderedListOfWarehouses(warehousesList, sortedMap);
//        return ModelMapper.fromWarehouseListToWarehouseDtoList(orderedList);
        return null;
    }

}
