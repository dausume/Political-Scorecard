package com.asc.politicalscorecard.objects;


public class Planet {

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