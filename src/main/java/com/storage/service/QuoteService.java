package com.storage.service;

import com.storage.models.Warehouse;
import com.storage.models.businessObject.Quote;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.validators.QuoteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Class that provides methods for creating and manipulating quote
 *
 * @author Pawel Konarzewski
 * @since 02/03/2021
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuoteService {

    private final WarehouseService warehouseService;
    private final PriceService priceService;

    /**
     * Method for creating a new Quote for user
     * with details of the possible contract and price
     *
     * @author Pawel Konarzewski
     * @since 02/03/2021
     */

    public Quote estimate(QuoteEstimateRequest request) {
        log.info("Start quote estimation with request: [{}]", request);
        QuoteValidator.validate(request);
        Warehouse warehouse = warehouseService.findByUuid(request.warehouseUuid());
        return generateQuote(request, warehouse);
    }

    private Quote generateQuote(QuoteEstimateRequest request, Warehouse warehouse) {
        BigDecimal price = priceService.calculatePrice(
                request.storageSize(),
                request.duration(),
                warehouse.getId());

        return Quote.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .postcode(warehouse.getPostcode())
                .warehouseName(warehouse.getName())
                .city(warehouse.getCity())
                .street(warehouse.getStreet())
                .price(price)
                .storageSize(request.storageSize())
                .startDate(request.startDate())
                .duration(request.duration())
                .extraServices(request.extraServices())
                .build();
    }


}
