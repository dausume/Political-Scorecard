package com.asc.politicalscorecard.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.asc.politicalscorecard.services.PlanetService;

public class Nation {

    private String id;

    private String nationName;

    private Planet homePlanet;

    

    public Nation() {
    }

    // Note : To create a Nation using a homePlanetId, overload the createNation with a string in the parameter location of homePlanet.
    public Nation(String id, String nationName, Planet homePlanet) {
        this.id = id;
        this.nationName = nationName;
        this.homePlanet = homePlanet;
    }

    public Nation(String nationName, Planet homePlanet) {
        this.nationName = nationName;
        this.homePlanet = homePlanet;
    }

    //make a shallow planet with only the id, this should not be used when updating both this
    //and the planet at the same time or it will cause data loss.  Only when quickly using the id to make the connection.
    public Nation(String nationName, String homePlanetId) {
        this.nationName = nationName;
        this.homePlanet = new Planet(id); 
    }

    // Getter
    public String getId() {
        return id;
    }

    // Getter
    public String getNationName() {
        return nationName;
    }
}