package com.storage.service;

import com.storage.models.Warehouse;
import com.storage.models.businessObject.Quote;
import com.storage.models.requests.QuoteEstimateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

//TODO create separate Email Service class
// integration with 3rd party Email API gateway?

/**
 * Class that provides methods for sending email with quotation
 * for guest users
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

    public Quote estimate(QuoteEstimateRequest estimate) {
        log.info("Start quote estimation with request: [{}]", estimate);
        estimate.isValid();
        var warehouse = warehouseService.findByUuid(estimate.getWarehouseUuid());
        // calculate price
        Quote quote = generateQuote(estimate, warehouse);
        return quote;
    }

    private Quote generateQuote(QuoteEstimateRequest estimate, Warehouse warehouse) {
        BigDecimal price = priceService.calculatePrice(
                estimate.getStorageSize(),
                estimate.getDuration(),
                warehouse.getId());


        return Quote.builder()
                .warehouseName(warehouse.getName())
                .city(warehouse.getCity())
                .street(warehouse.getStreet())
                .price(price)
                .build();
    }


}
