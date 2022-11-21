package com.storage.service;

import com.storage.models.Price;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceService extends AbstractService<Price>{

    private PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        super(Price.class, priceRepository);
        this.priceRepository = priceRepository;
    }

    //TODO add pricing calculation logic
    public BigDecimal calculatePrice(QuoteEstimateRequest estimate) {
        return BigDecimal.TEN;
    }
}
