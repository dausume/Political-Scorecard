package com.asc.politicalscorecard.objects;

import javax.persistence.ManyToOne;

public class State {
    private String id;

    private String stateName;

    @ManyToOne
    private Nation parentNation;

    public State(String id, String stateName) {
        this.id = id;
        this.stateName = stateName;
    }

     // Note : To create a Sate using a Nation Id, overload the createState with a string in the parameter location of parentNation.
     public State(String id, String stateName, Nation parentNation) {
        this.id = id;
        this.stateName = stateName;
        this.parentNation = parentNation;
    }

    public State(String stateName, Nation parentNation) {
        this.stateName = stateName;
        this.parentNation = parentNation;
    }

    public String getId() {
        return id;
    }

    public String getStateName() {
        return stateName;
    }
}