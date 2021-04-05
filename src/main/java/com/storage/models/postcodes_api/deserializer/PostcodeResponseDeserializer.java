package com.storage.models.postcodes_api.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.storage.models.postcodes_api.response.PostcodeResponse;
import com.storage.models.postcodes_api.response.Result;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PostcodeResponseDeserializer extends StdDeserializer<PostcodeResponse> {

    public PostcodeResponseDeserializer() {
        super((JavaType) null);
    }

    protected PostcodeResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    protected PostcodeResponseDeserializer(JavaType valueType) {
        super(valueType);
    }


    @Override
    public PostcodeResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        log.info("Enter PostcodeResponseDeserializer -> deserialize() with JsonParser: {}", p);

        JsonNode node = p.getCodec().readTree(p);
        var status = node.get("status").asInt();

        JsonNode results = node.get("result");

        List<Result> resultsList = new ArrayList<>();
        if (results.isArray()) {
            for (JsonNode objNode : results) {
                var result = objNode.get("result");
                var postcode = result.get("postcode").asText();
                var longitude = result.get("longitude").asDouble();
                var latitude = result.get("latitude").asDouble();
                resultsList.add(
                        Result.builder()
                                .postcode(postcode)
                                .longitude(longitude)
                                .latitude(latitude)
                                .build()
                );
            }
        } else {
            var postcode = results.get("postcode").asText();
            var longitude = results.get("longitude").asDouble();
            var latitude = results.get("latitude").asDouble();
            resultsList.add(
                    Result.builder()
                            .postcode(postcode)
                            .longitude(longitude)
                            .latitude(latitude)
                            .build()
            );
        }

        return PostcodeResponse.builder()
                .status(status)
                .result(resultsList)
                .build();
    }
}
