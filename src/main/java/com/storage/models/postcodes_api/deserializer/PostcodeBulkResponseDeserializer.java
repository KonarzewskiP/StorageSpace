package com.storage.models.postcodes_api.deserializer;

import com.google.gson.*;
import com.storage.models.postcodes_api.response.PostcodeBulkResponse;
import com.storage.models.postcodes_api.response.ResultSingleResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that deserialize JSON from rest API call to
 * PostcodeBulkResponse object.
 *
 * @author Pawel Konarzewski
 * @since 07/03/2021
 */
@Slf4j
public class PostcodeBulkResponseDeserializer implements JsonDeserializer<PostcodeBulkResponse> {

    /**
     * Method that deserialize JSON to PostcodeBulkResponse object.
     * <p>
     * <p>
     * Returns: PostcodeSingleResponse object with longitude and latitude coordinates. If Postcode
     * is invalid, then returns PostcodeSingleResponse object with error property.
     *
     * @author Pawel Konarzewski
     * @since 07/03/2021
     */

    @Override
    public PostcodeBulkResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        log.info("Enter CustomDeserializer -> deserialize()");
        PostcodeBulkResponse postcodeResponse = new PostcodeBulkResponse();
        List<ResultSingleResponse> list = new ArrayList<>();
        JsonObject jsonObject = json.getAsJsonObject();

        postcodeResponse.setStatus(jsonObject.get("status").getAsInt());

        for (JsonElement jObject : jsonObject.get("result").getAsJsonArray()) {
            JsonElement jElement = jObject.getAsJsonObject().get("result");
            ResultSingleResponse temp;
            if (jElement.isJsonNull()) {
                temp = ResultSingleResponse.builder()
                        .postcode(jObject.getAsJsonObject().get("query").getAsString())
                        .latitude(null)
                        .longitude(null)
                        .build();
            } else {
                temp = ResultSingleResponse.builder()
                        .postcode(jElement.getAsJsonObject().get("postcode").getAsString())
                        .latitude(jElement.getAsJsonObject().get("latitude").getAsDouble())
                        .longitude(jElement.getAsJsonObject().get("longitude").getAsDouble())
                        .build();

            }
            list.add(temp);
        }
        postcodeResponse.setResult(list);
        return postcodeResponse;
    }
}
