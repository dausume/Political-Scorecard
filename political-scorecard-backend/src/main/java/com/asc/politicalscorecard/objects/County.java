package com.asc.politicalscorecard.objects;

public class County {
    private String id;

    private String countyName;

    // Constructor
    public County(String id, String countyName) {
        this.id = id;
        this.countyName = countyName;
    }

    // Getter
    public String getId() {
        return id;
    }

    // Getter
    public String getCountyName() {
        return countyName;
    }
}