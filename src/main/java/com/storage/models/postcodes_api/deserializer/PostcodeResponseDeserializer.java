package com.storage.models.postcodes_api.deserializer;

import com.google.gson.*;
import com.storage.models.postcodes_api.response.PostcodeSingleResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * Class that deserialize JSON from rest API call to
 * PostcodeSingleResponse object.
 *
 * @author Pawel Konarzewski
 * @since 07/03/2021
 */

@Slf4j
public class PostcodeResponseDeserializer implements JsonDeserializer<PostcodeSingleResponse> {

    /**
     * Method that deserialize JSON to PostcodeSingleResponse object.
     * <p>
     *
     * Returns: PostcodeSingleResponse object with a list of ResultSingleResponse objects.
     * The list contains longitudes and latitudes of specific postcode. If postcode in JSON
     * is not valid then ResultSingleResponse object is created with latitude and longitude null values.
     *
     * @author Pawel Konarzewski
     * @since 07/03/2021
     */

    @Override
    public PostcodeSingleResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        log.info("Enter PostcodeResponseDeserializer -> deserialize():");
        var response = new PostcodeSingleResponse();
        JsonObject jsonObject = json.getAsJsonObject();

        if (!jsonObject.get("status").getAsString().equals("200")) {
            response.setStatus(jsonObject.get("status").getAsString());
            response.setError(jsonObject.get("error").getAsString());
            return response;
        }

        response.setStatus(jsonObject.get("status").getAsString());
        var jsonObjectResult = jsonObject.get("result").getAsJsonObject();
        response.setPostcode(jsonObjectResult.get("postcode").getAsString());
        response.setLatitude(jsonObjectResult.get("latitude").getAsDouble());
        response.setLongitude(jsonObjectResult.get("longitude").getAsDouble());
        return response;
    }
}
