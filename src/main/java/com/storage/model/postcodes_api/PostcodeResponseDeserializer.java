package com.storage.model.postcodes_api;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

@Slf4j
public class PostcodeResponseDeserializer implements JsonDeserializer<PostcodeSingleResponse> {

    @Override
    public PostcodeSingleResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        log.info("Enter CustomDeserializer -> deserialize():");
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
