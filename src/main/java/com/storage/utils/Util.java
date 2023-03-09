package com.storage.utils;

import com.storage.models.StorageRoom;
import com.storage.models.enums.StorageSize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//TODO
// rename utility class, make it more specific
public interface Util {

    /**
     * the method is to create storage rooms for the newly added warehouse.
     * It will create one room of each size.
     *
     * @return list of storages
     */

    static List<StorageRoom> createStorageRoomsList() {
        return Arrays.stream(StorageSize.values())
                .map(size -> StorageRoom.builder()
                        .storageSize(size)
                        .build())
                .collect(Collectors.toList());
    }


//    static double calculateDistance(PostcodeResultDTO postcode1, PostcodeDTO postcode2) {
//        return calculateDistance(postcode1.getLatitude(), postcode1.getLongitude(), postcode2.getPostcodeResultDTO().getLatitude(), postcode2.getPostcodeResultDTO().getLongitude());
//    }

    /**
     * Calculates the distance in km between two points with
     * latitude and longitude coordinates by
     * using the haversine formula
     * <p>
     *
     * @param lat1 latitude of first postcode
     * @param lng1 longitude of first postcode
     * @param lat2 latitude of second postcode
     * @param lng2 longitude of second postcode
     *             <p>
     * @return distance between two postcodes in km
     */

    private static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        var earthRadius = 6371;
        var dLat = Math.toRadians(lat2 - lat1);
        var dLon = Math.toRadians(lng2 - lng1);
        var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }


}
