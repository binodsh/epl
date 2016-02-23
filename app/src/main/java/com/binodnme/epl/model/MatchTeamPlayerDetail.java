package com.binodnme.epl.model;

import java.io.Serializable;

/**
 * Created by binodnme on 2/22/16.
 */
public class MatchTeamPlayerDetail implements Serializable {
    private long Id;
    private String name;
    private String position;
    private long number;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
