package com.storage.models.businessObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.models.enums.ExtraServices;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Object used to send a replay to an
 * enquiry about specific warehouse
 */
@Builder
@Data
public class Quote {

    private String firstName;
    private String surname;
    private String email;
    private String postcode;
    private String warehouseName;
    private String city;
    private String street;

    private BigDecimal price;
    private BigDecimal actualPrice;

    private StorageSize storageSize;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private StorageDuration duration;
    private Set<ExtraServices> extraServices;

    private BigDecimal calculatePriceWithDiscount(BigDecimal price, double discount) {
        return price.multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(discount));
    }

    // TODO need improvment - pricing concept is wrong

  /*  public BigDecimal quote() {
        BigDecimal price = BigDecimal.valueOf(storageSize.getPrice());

        switch (duration) {
            case UP_TO_4_WEEKS -> calculatePriceWithDiscount(price, 0.97);
            case UP_TO_8_WEEKS -> calculatePriceWithDiscount(price, 0.93);
            case UP_TO_6_MONTHS -> calculatePriceWithDiscount(price, 0.90);
            case UP_TO_1_YEAR -> calculatePriceWithDiscount(price, 0.85);
            case PLUS_1_YEAR -> calculatePriceWithDiscount(price, 0.75);
            default -> calculatePriceWithDiscount(price, 0.70);
        }


    }*/
}
