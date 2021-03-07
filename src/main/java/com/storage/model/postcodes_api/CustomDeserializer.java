package com.storage.model.postcodes_api;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class CustomDeserializer implements JsonDeserializer<ResultBulkResponse>  {

    @Override
    public ResultBulkResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
       log.info("Enter CustomDeserializer -> deserialize()");
        ResultBulkResponse postcodeResponse = new ResultBulkResponse();
        List<ResultSingleResponse> list = new ArrayList<>();
        JsonObject jsonObject = json.getAsJsonObject();

        System.out.println("--------------------------------- CustomDeserializer ----------------------------------");

        log.info("------------> JsonObject: " + jsonObject);

        Iterator<JsonElement> jsonIter = jsonObject.get("result").getAsJsonArray().iterator();
        System.out.println("--------------------------------- Iterator<JsonElement> ----------------------------------");
        while(jsonIter.hasNext()){
            JsonElement jElement = jsonIter.next().getAsJsonObject().get("result");
            var temp = ResultSingleResponse.builder()
                    .postcode(jElement.getAsJsonObject().get("postcode").getAsString())
                    .latitude(jElement.getAsJsonObject().get("latitude").getAsDouble())
                    .longitude(jElement.getAsJsonObject().get("longitude").getAsDouble())
                    .build();
            log.info("------------> ResultSingleResponse: " + temp);
            list.add(temp);
        }
        log.info("------------> List<ResultSingleResponse> : ");
        list.forEach(System.out::println);
        return postcodeResponse;
    }
}
