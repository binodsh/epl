package com.binodnme.epl.model;

/**
 * Created by binodnme on 2/23/16.
 */
public class MatchDay {
    private long id;
    private String name;
    Boolean isCurrentMatchDay;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCurrentMatchDay() {
        return isCurrentMatchDay;
    }

    public void setCurrentMatchDay(Boolean currentMatchDay) {
        isCurrentMatchDay = currentMatchDay;
    }
}
