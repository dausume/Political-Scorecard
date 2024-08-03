package com.asc.politicalscorecard.json.dtos;

import com.asc.politicalscorecard.json.deserializer.PlanetDeserializer;
import com.asc.politicalscorecard.json.serializer.PlanetSerializer;
import com.asc.politicalscorecard.objects.Planet;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonSerialize(using = PlanetSerializer.class)
@JsonDeserialize(using = PlanetDeserializer.class)
public class PlanetDTO extends AbstractDTO {
    private String planetName;

    // For existing Planet Objects.
    public PlanetDTO(String id, String planetName) {
        System.out.println("creating DTO for pre-existing Planet object.");
        super.setId(id);
        this.planetName = planetName;
    }

    // For new Planet objects.
    public PlanetDTO(String planetName) {
        System.out.println("creating DTO for data to be turned into a new planet.");
        // Automate creating a uuid, then set it.
        super.setId();
        this.planetName = planetName;
    }

    // For converting planet object
    public PlanetDTO(Planet planet) {
        System.out.println("Converting Planet Object into a Planet DTO.");
        // Automate creating a uuid, then set it.
        super.setId(planet.getId());
        this.planetName = planet.getPlanetName();
    }

    // Getter and Setter for planetName
    public String getPlanetName() {
        System.out.println("getting planet name in DTO");
        return planetName;
    }

    public void setPlanetName(String planetName) {
        System.out.println("setting planet value in DTO");
        this.planetName = planetName;
    }

    public Planet toEntity() {
        if(this.getId() == null) // Used in the case where you are moving around objects that have not been put into the database.
        {
            return new Planet(getId(), planetName);
        }
        else // Used in the case where you are moving around objects that are not yet in the database.
        {
            return new Planet(planetName);
        }
    }

    @Override
    public String toString() {
        return "PlanetDTO [id=" + getId() + ", planetName=" + planetName + "]";
    }
}
