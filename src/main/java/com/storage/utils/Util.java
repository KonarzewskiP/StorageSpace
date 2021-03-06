package com.storage.utils;

import com.storage.models.StorageRoom;
import com.storage.models.dto.externals.postcode.Result;
import com.storage.models.enums.Size;
import com.storage.models.dto.externals.postcode.PostcodeResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Util {

    static List<StorageRoom> createStorageRoomsList() {
        return Arrays.stream(Size.values())
                .map(size -> StorageRoom.builder()
                        .size(size)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Calculates the distance in km between two points with
     * latitude and longitude coordinates by
     * using the haversine formula
     * <p>
     * Params: lat1 - latitude of first postcode
     * lng1 - longitude of first postcode
     * lat2 - latitude of second postcode
     * lng2 - longitude of second postcode
     * <p>
     * Returns: distance between two postcodes in km
     */
    static double calculateDistance(Result postcode1, PostcodeResponse postcode2) {
        return calculateDistance(postcode1.getLatitude(), postcode1.getLongitude(), postcode2.getResult().getLatitude(), postcode2.getResult().getLongitude());
    }

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
