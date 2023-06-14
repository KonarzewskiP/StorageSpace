
package com.storage.utils.mapper;

import com.storage.models.Price;
import com.storage.models.dto.PriceDTO;
import com.storage.service.WarehouseService;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    private final WarehouseService warehouseService;

    public PriceMapper(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    //ModelMapper library does not have possibility to map Record to POJO
    public PriceDTO map(Price price) {
        String warehouseUuid = warehouseService.findUuidById(price.getWarehouseId());

        return new PriceDTO(
                price.getUuid(),
                warehouseUuid,
                price.getTimeUnit(),
                price.getTelephoneBoxBasePrice(),
                price.getLargeGardenShed(),
                price.getOneAndHalfGarages(),
                price.getThreeSingleGarages(),
                price.getUpTo2WeeksMultiplier(),
                price.getUpTo4WeeksMultiplier(),
                price.getUpTo8WeeksMultiplier(),
                price.getUpTo6MonthsMultiplier(),
                price.getUpTo1YearMultiplier(),
                price.getPlus1YearMultiplier());
    }
}
