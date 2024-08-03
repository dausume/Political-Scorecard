package com.asc.politicalscorecard.json.deserializer;

import com.asc.politicalscorecard.json.dtos.NationDTO;
import com.asc.politicalscorecard.objects.Planet;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class NationDeserializer extends JsonDeserializer<NationDTO> {

    @Override
    public NationDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        String id = node.has("id") ? node.get("id").asText(null) : null;
        String nationName = node.has("nationName") ? node.get("nationName").asText(null) : null;
        JsonNode homePlanetNode = node.get("homePlanet");
        
        Planet homePlanet = null;

        if (nationName == null) {
            throw new IOException("Missing required field: nationName");
        }

        if (homePlanetNode != null) {
            if (homePlanetNode.has("id")) {
                homePlanet = new Planet(homePlanetNode.get("id").asText(null), homePlanetNode.get("planetName").asText(null));
            } else {
                homePlanet = new Planet(homePlanetNode.asText(null), null);
            }
        }

        if (id != null) {
            return new NationDTO(id, nationName, homePlanet);
        } else {
            return new NationDTO(nationName, homePlanet);
        }
    }
}
