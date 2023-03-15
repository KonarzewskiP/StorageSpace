package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.Price;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PriceService extends AbstractService<Price> {

    private final PriceRepository priceRepository;
    private final WarehouseService warehouseService;

    @Autowired
    public PriceService(PriceRepository priceRepository,
                        WarehouseService warehouseService) {
        super(Price.class, priceRepository);
        this.priceRepository = priceRepository;
        this.warehouseService = warehouseService;
    }

    /**
     * Calculates the price per week of the storage room for the specific warehouse.
     * <p>
     * Returns: price for the storage room per week.
     *
     * @author Pawel Konarzewski
     */
    public BigDecimal calculatePrice(StorageSize storageSize, StorageDuration duration, Long warehouseId) {
        Optional<Price> priceOptional = priceRepository.getByWarehouseId(warehouseId);
        if (priceOptional.isEmpty()) {
            String warehouseUuid = warehouseService.findUuidById(warehouseId);
            throw new NotFoundException("Price table not found for warehouse with UUID: " + warehouseUuid);
        }
        Price price = priceOptional.get();

        BigDecimal basePrice = getBasePriceForStorageRoom(storageSize, price);
        BigDecimal priceMultiplier = getMultiplierForStorageDuration(duration, price);

        return basePrice.multiply(priceMultiplier);
    }

    private BigDecimal getMultiplierForStorageDuration(StorageDuration duration, Price price) {

        return switch (duration) {
            case UP_TO_2_WEEKS -> price.getUpTo2WeeksMultiplier();
            case UP_TO_4_WEEKS -> price.getUpTo4WeeksMultiplier();
            case UP_TO_8_WEEKS -> price.getUpTo8WeeksMultiplier();
            case UP_TO_6_MONTHS -> price.getUpTo6MonthsMultiplier();
            case UP_TO_1_YEAR -> price.getUpTo1YearMultiplier();
            case PLUS_1_YEAR -> throw new BadRequestException("Individual Pricing. Contact Customer Service.");
            default -> throw new BadRequestException("Pricing not implemented for duration: " + duration);
        };
    }

    private BigDecimal getBasePriceForStorageRoom(StorageSize storageSize, Price price) {
        return switch (storageSize) {
            case TELEPHONE_BOX -> price.getTelephoneBoxBasePrice();
            case LARGE_GARDEN_SHED -> price.getLargeGardenShed();
            case ONE_AND_HALF_GARAGES -> price.getOneAndHalfGarages();
            case THREE_SINGLE_GARAGES -> price.getThreeSingleGarages();
            default -> throw new BadRequestException("Pricing not implemented for size: " + storageSize);
        };
    }
}
