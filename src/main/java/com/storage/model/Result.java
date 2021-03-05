package com.storage.model;

import lombok.Data;

import java.util.List;

/**
 * This is a model class to hold precise location information.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 *
 */


@Data
public class Result {

    private String postcode;
    private double longitude;
    private double latitude;
}
