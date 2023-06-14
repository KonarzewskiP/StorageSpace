package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.Price;
import com.storage.models.dto.PriceDTO;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import com.storage.models.enums.TimeUnit;
import com.storage.models.requests.CreatePriceRequest;
import com.storage.repositories.PriceRepository;
import com.storage.utils.StringUtils;
import com.storage.utils.UuidGenerator;
import com.storage.utils.mapper.PriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PriceService extends AbstractService<Price> {

    private final PriceRepository priceRepository;
    private final WarehouseService warehouseService;
    private final PriceMapper priceMapper;

    @Autowired
    public PriceService(PriceRepository priceRepository,
                        WarehouseService warehouseService, PriceMapper priceMapper) {
        super(Price.class, priceRepository);
        this.priceRepository = priceRepository;
        this.warehouseService = warehouseService;
        this.priceMapper = priceMapper;
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

    public PriceDTO create(CreatePriceRequest request) {
        Long warehouseId = warehouseService.findIdByUuid(request.warehouseUuid());

        Price price = Price.builder()
                .uuid(UuidGenerator.next())
                .warehouseId(warehouseId)
                .timeUnit(TimeUnit.WEEK)
                .telephoneBoxBasePrice(request.telephoneBoxBasePrice())
                .largeGardenShed(request.largeGardenShed())
                .oneAndHalfGarages(request.oneAndHalfGarages())
                .threeSingleGarages(request.threeSingleGarages())
                .upTo2WeeksMultiplier(request.upTo2WeeksMultiplier())
                .upTo4WeeksMultiplier(request.upTo4WeeksMultiplier())
                .upTo8WeeksMultiplier(request.upTo8WeeksMultiplier())
                .upTo6MonthsMultiplier(request.upTo6MonthsMultiplier())
                .upTo1YearMultiplier(request.upTo1YearMultiplier())
                .plus1YearMultiplier(request.plus1YearMultiplier())
                .build();

        Price savedPrice = priceRepository.save(price);
        return priceMapper.map(savedPrice);
    }

    public Page<PriceDTO> getAll(String uuid, Pageable pageable) {
        Long warehouseId = warehouseService.findIdByUuid(uuid);

        Specification<Price> spec = Specification.where((root, cq, cb) -> cb.equal(root.get("warehouseId"), warehouseId));

        return priceRepository.findAll(spec, pageable).map(priceMapper::map);
    }

    public int deleteByUuid(String uuid) {
        if (StringUtils.isBlank(uuid))
            throw new BadRequestException("Uuid can not be null or empty!");

        return priceRepository.deleteByUuid(uuid);
    }
}
