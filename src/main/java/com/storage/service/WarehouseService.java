package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.models.Warehouse;
import com.storage.models.dto.WarehouseDto;
import com.storage.models.dto.postcode.PostcodeDTO;
import com.storage.models.dto.postcode.PostcodeDetails;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.repositories.WarehouseRepository;
import com.storage.utils.DistanceUtils;
import com.storage.utils.StringUtils;
import com.storage.utils.UuidGenerator;
import com.storage.utils.mapper.WarehouseMapper;
import com.storage.validators.WarehouseDTOValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * @author Pawel Konarzewski
 * @version 1.0
 */

@Slf4j
@Service
@Transactional
public class WarehouseService extends AbstractService<Warehouse> {
    private static final Logger LOG = LoggerFactory.getLogger(WarehouseService.class);

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final PostcodeService postcodeService;

    public WarehouseService(WarehouseRepository warehouseRepository,
                            WarehouseMapper warehouseMapper,
                            PostcodeService postcodeService
    ) {
        super(Warehouse.class, warehouseRepository);
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
        this.postcodeService = postcodeService;
    }

    /**
     * The method add the warehouse to the database.
     *
     * @param request object to be saved.
     * @return Saved warehouse to the database as dto object.
     */

    public WarehouseDto create(CreateWarehouseRequest request) {
        WarehouseDTOValidator.validate(request);
        Warehouse warehouse = Warehouse.builder()
                .uuid(UuidGenerator.next())
                .name(request.name())
                .city(request.city())
                .street(request.street())
                .totalRentalAreaInM2(request.totalRentalAreaInM2())
                .postcode(request.postcode())
                .build();

        //TODO implement message queue to execute tasks of getting postcode details asynchronously
        PostcodeDTO postcodeDetails = postcodeService.getDetails(request.postcode());
        if (postcodeDetails != null && postcodeDetails.getResult() != null) {
            warehouse.setPostcode(postcodeDetails.getResult().getPostcode());
            warehouse.setLat(postcodeDetails.getResult().getLat());
            warehouse.setLng(postcodeDetails.getResult().getLng());
        }

        warehouse = warehouseRepository.save(warehouse);
        return warehouseMapper.map(warehouse);
    }

    /**
     * The method retrieves all warehouses from the database.
     *
     * @return List of warehouses from the database with details.
     */
    public Page<WarehouseDto> getAll(Pageable pageable) {
        return warehouseRepository.findAll(pageable).map(warehouseMapper::map);
    }


    /**
     * Sort all warehouses from the database in ascending order by distance from
     * the postcode given by user.
     *
     * @param postcode postcode given by the user.
     * @return list of ordered warehouses in descending order. From the nearest to the farthest
     */
    public List<WarehouseDto> getSortedByDistanceFromPostcode(String postcode) {
        if (StringUtils.isBlank(postcode))
            throw new BadRequestException("Postcode can not be null or empty");

        PostcodeDTO postcodeDTO = postcodeService.getDetails(postcode);
        if (postcodeDTO == null) {
            LOG.info("Can not obtain postcode [{}] details to calculate distance from warehouses. ", postcode);
            return warehouseRepository.findAll().stream().map(warehouseMapper::map).toList();
        }

        PostcodeDetails postcodeDetails = postcodeDTO.getResult();
        List<Warehouse> warehouses = warehouseRepository.findAll();

        return warehouses.stream()
                .sorted(Comparator.comparing(warehouse ->
                        DistanceUtils.betweenTwoPoints(warehouse.getLat(), warehouse.getLng(), postcodeDetails.getLat(), postcodeDetails.getLng())))
                .map(warehouseMapper::map)
                .toList();
    }

}
