package com.storage.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.models.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
public class Quote {

    String firstName;
    String surname;
    String email;
    String postcode;
    String warehouseName;

    Size size;
    TypeOfStorage type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate startDate;
    StorageDuration duration;
    Boolean needMoreThanOneLocation;
    Set<ExtraServices> extraServices;
    Reason reason;

    private BigDecimal calculatePriceWithDiscount(BigDecimal price, double discount) {
        return price.multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(discount));
    }

    public BigDecimal quote() {
        BigDecimal price = BigDecimal.valueOf(size.getPrice());

        switch (duration) {
            case TWO_FOUR_WEEKS -> {
                return calculatePriceWithDiscount(price, 0.97);
            }
            case FOUR_EIGHT_WEEKS -> {
                return calculatePriceWithDiscount(price, 0.93);
            }
            case EIGHT_TWELVE_WEEKS -> {
                return calculatePriceWithDiscount(price, 0.90);
            }
            case THREE_SIX_MONTHS -> {
                return calculatePriceWithDiscount(price, 0.85);
            }
            case SIX_TWELVE_MOTHS -> {
                return calculatePriceWithDiscount(price, 0.80);
            }
            case PLUS_1_YEAR -> {
                return calculatePriceWithDiscount(price, 0.75);
            }
            default -> {
                return calculatePriceWithDiscount(price, 0.70);
            }
        }
    }


}
