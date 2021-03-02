package com.storage.model;

import com.storage.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Value
@AllArgsConstructor
public class Quote {

    String firstName;
    String surname;
    String email;
    String phoneNumber;
    String postcode;

    Size size;
    TypeOfAccount type;
    LocalDateTime startDate;
    StorageDuration duration;
    Boolean needMoreThanOneLocation;
    Set<ExtraServices> extraServices;
    Reason reason;


    private BigDecimal calculatePriceWithDiscount(BigDecimal price, double discount) {
        return price.multiply(BigDecimal.valueOf(7)).multiply(BigDecimal.valueOf(0.67));
    }

    public BigDecimal quote(StorageDuration duration, Size size) {
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
