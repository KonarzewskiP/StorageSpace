package com.storage.utils;

public class DistanceUtils {

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

    public static double betweenTwoPoints(double lat1, double lng1, double lat2, double lng2) {
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
