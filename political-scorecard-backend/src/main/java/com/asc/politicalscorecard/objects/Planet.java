package com.asc.politicalscorecard.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Planet {
    @Id
    private String id;

    private String planetName;

    public Planet() {
    }

    // Constructor
    public Planet(String id, String planetName) {
        this.id = id;
        this.planetName = planetName;
    }

    public Planet(String planetName) {
        this.planetName = planetName;
    }

    // Getter
    public String getId() {
        return id;
    }

    // Getter
    public String getPlanetName() {
        return planetName;
    }
}