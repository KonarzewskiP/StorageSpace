package com.storage.service;

import com.storage.models.Warehouse;
import com.storage.models.businessObject.Quote;
import com.storage.models.requests.QuoteEstimateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    /**
     * Method that takes details from Quote object and send email
     * with quotation to provided email address.
     *
     * @param quote
     * @return <code>QuoteResponse</code> object
     * @author Pawel Konarzewski
     * @since 02/03/2021
     */

    public Quote estimate(QuoteEstimateRequest estimate) {
        log.info("Start quote estimation with request: [{}]", estimate);
        estimate.isValid();
        var warehouse = warehouseService.findByUuid(estimate.getWarehouseUuid());
        Quote quote = generateQuote(estimate, warehouse);
        //TODO send email to user
        return quote;
    }

    private Quote generateQuote(QuoteEstimateRequest estimate, Warehouse warehouse) {
        Quote quote = modelMapper.map(estimate, Quote.class);

        BigDecimal price = priceService.calculatePrice(estimate);
        BigDecimal actualPrice = priceService.priceAfterDiscount(estimate);
        quote.setPrice(price);
        quote.setActualPrice(actualPrice);

        quote.setWarehouseName(warehouse.getName());
        quote.setCity(warehouse.getCity());
        quote.setStreet(warehouse.getStreet());
        return quote;
    }
}
