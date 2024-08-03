package com.asc.politicalscorecard.json.serializer;

import com.asc.politicalscorecard.json.dtos.NationDTO;
import com.asc.politicalscorecard.json.dtos.PlanetDTO;
import com.asc.politicalscorecard.databases.daos.PlanetDAO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

public class NationSerializer extends JsonSerializer<NationDTO> {

    private final PlanetDAO planetDAO;

    // Constructor injection of the PlanetDAO to allow access to the database
    @Autowired
    public NationSerializer(PlanetDAO planetDAO) {
        this.planetDAO = planetDAO;
    }

    @Override
    public void serialize(NationDTO nationDTO, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        System.out.println("Inside nation serializer");

        // Start writing the JSON object
        gen.writeStartObject();
        
        // Write the ID field of the NationDTO
        gen.writeStringField("id", nationDTO.getId());
        
        // Write the nationName field of the NationDTO
        gen.writeStringField("nationName", nationDTO.getNationName());

        // Check if the homePlanet field in NationDTO is not null
        if (nationDTO.getHomePlanet() != null) {
            // Get the ID of the home planet
            String planetId = nationDTO.getHomePlanet().getId();
            // Get the home planet DTO
            PlanetDTO planetDTO = nationDTO.getHomePlanetDTO();

            // Check if the home planet DTO has only the ID (i.e., other fields are null)
            if (planetDTO.getPlanetName() == null) {
                // Fetch the full planet details from the PlanetDAO
                try {
                    planetDTO = planetDAO.read(planetId);
                    System.out.println("Retrieved full planet details from DAO for planet ID: " + planetId);
                } catch (Exception e) {
                    System.out.println("Error reading planet with ID: " + planetId + " - " + e.getMessage());
                    planetDTO = null; // Handle the case where the planet retrieval fails
                }
            }

            // Write the homePlanet JSON object if planetDTO is not null
            if (planetDTO != null) {
                gen.writeObjectFieldStart("homePlanet");
                gen.writeStringField("id", planetDTO.getId());
                gen.writeStringField("planetName", planetDTO.getPlanetName());
                gen.writeEndObject();
            } else {
                // If the planetDTO is null, write a null field for homePlanet
                gen.writeNullField("homePlanet");
            }
        } 
        else 
        {
            // If the homePlanet field is null in the NationDTO, write a null field
            gen.writeNullField("homePlanet");
        }

        // End writing the JSON object
        gen.writeEndObject();
    }
}
