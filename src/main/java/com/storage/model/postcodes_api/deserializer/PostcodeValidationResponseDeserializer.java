package com.storage.model.postcodes_api.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

@Slf4j
public class PostcodeValidationResponseDeserializer implements JsonDeserializer<Boolean> {

    /**
     * Method that deserialize JSON to Boolean object.
     * <p>
     * <p>
     * Returns: Boolean object with with result of postcode validation. True if postcode
     * is valid, false if postcode is not valid.
     *
     * @author Pawel Konarzewski
     * @since 09/03/2021
     */
    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        log.info("Enter PostcodeValidationResponseDeserializer -> deserialize():");
        return json.getAsJsonObject().get("result").getAsBoolean();
    }
}
