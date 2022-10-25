package com.storage.service;

import com.storage.models.WarehouseSpec;
import com.storage.models.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* TODO need to think it through
 - what is the usage of the class
 - how to make it more clear
 */
/**
 * @author Pawel Konarzewski
 * @version 1.0
 */

@Slf4j
@Service
public class SpecificationService {

    /**
     * Return the specific type of specification with details.
     * <p>
     * @param specType enum with information to update.
     * @return Exact data about the specification type.
     */

    public WarehouseSpec getSpec(SpecType specType) {
        log.info("Enter SpecificationController -> getSpec() of: " + specType);
        Map<String, Object> warehouseSpec = new HashMap<>();

        warehouseSpec.put("ReasonForStorage",List.of(Reason.values()));
        warehouseSpec.put("StorageDuration",List.of(StorageDuration.values()));

        if (specType == SpecType.PREMIUM) {
            warehouseSpec.put("TypeOfStorage", List.of(TypeOfStorage.values()));
            warehouseSpec.put("ExtraServices",List.of(ExtraServices.values(), "VAN_HIRE"));
        } else if (specType == SpecType.REGULAR) {
            warehouseSpec.put("TypeOfStorage", List.of(TypeOfStorage.HOME,TypeOfStorage.STUDENT));
        }
        return new WarehouseSpec(warehouseSpec);
    }
}
