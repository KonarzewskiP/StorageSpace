package com.storage.service;

import com.storage.models.WarehouseSpec;
import com.storage.models.enums.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecificationService {

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
