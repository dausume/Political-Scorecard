package com.asc.politicalscorecard.json.dtos;

import com.asc.politicalscorecard.json.deserializer.NationDeserializer;
import com.asc.politicalscorecard.json.serializer.NationSerializer;
import com.asc.politicalscorecard.objects.Nation;
import com.asc.politicalscorecard.objects.Planet;
import com.asc.politicalscorecard.json.dtos.PlanetDTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = NationSerializer.class)
@JsonDeserialize(using = NationDeserializer.class)
public class NationDTO extends AbstractDTO {
    private String nationName;
    private Planet homePlanet;

    // For existing Nation Objects.
    public NationDTO(String id, String nationName, Planet homePlanet) {
        super.setId(id);
        this.nationName = nationName;
        this.homePlanet = homePlanet;
    }

    // For new Nation objects.
    public NationDTO(String nationName, Planet homePlanet) {
        super.setId();  // Automatically generates a new UUID
        this.nationName = nationName;
        this.homePlanet = homePlanet;
    }

    // Getter and Setter for nationName
    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    // Getter and Setter for homePlanet
    public PlanetDTO getHomePlanetDTO() {
        return new PlanetDTO(homePlanet);
    }

    // Getter and Setter for homePlanet
    public Planet getHomePlanet() {
        return homePlanet;
    }

    public String getHomePlanetId() {
        if(getHomePlanet() != null)
        {
            System.out.println("Got a Planet in getHomePlanetId!");
            System.out.println(getHomePlanet());
            return homePlanet.getId();
        }
        else{
            return null;
        }
    }

    // To set a homePlanet via Id, use the method in the service.
    public void setHomePlanet(Planet homePlanet) {
        this.homePlanet = homePlanet;
    }

    @Override
    public Nation toEntity() {
        if (this.getId() == null || this.getId().isEmpty()) {
            // Case when the DTO is for a new Nation to be saved
            return new Nation(getId(), getNationName(), getHomePlanet());
        } else {
            // Case when the DTO represents an existing Nation
            return new Nation(getId(), getNationName(), getHomePlanet());
        }
    }

    @Override
    public String toString() {
        return "NationDTO [id=" + getId() + ", nationName=" + nationName + ", homePlanet=" + getHomePlanetId() + "]";
    }
}
