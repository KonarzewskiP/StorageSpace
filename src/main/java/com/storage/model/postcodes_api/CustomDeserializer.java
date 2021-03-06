package com.storage.model.postcodes_api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomDeserializer implements JsonDeserializer<ResultBulkResponse>  {
    @Override
    public ResultBulkResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResultBulkResponse postcodeResponse = new ArrayList<>();
        JsonObject jsonObject = json.getAsJsonObject();
        System.out.println("--------------------------------- CustomDeserializer ----------------------------------");
        System.out.println(jsonObject);

        Iterator<JsonElement> jsonIter = jsonObject.get("result").getAsJsonArray().iterator();
        System.out.println("--------------------------------- Iterator<JsonElement> ----------------------------------");
        while(jsonIter.hasNext()){
            JsonElement jElement = jsonIter.next().getAsJsonObject().get("result");
            var temp = ResultSingleResponse.builder()
                    .postcode(jElement.getAsJsonObject().get("postcode").getAsString())
                    .latitude(jElement.getAsJsonObject().get("latitude").getAsDouble())
                    .longitude(jElement.getAsJsonObject().get("longitude").getAsDouble())
                    .build();
            System.out.println(temp);
            postcodeResponse.add(PostcodeSingleResponse.builder().resultSingleResponse(temp).build());
        }
        return postcodeResponse;
    }
}
