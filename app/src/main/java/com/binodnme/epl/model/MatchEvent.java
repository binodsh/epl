package com.binodnme.epl.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by binodnme on 2/24/16.
 */
public abstract class MatchEvent implements Serializable, Comparable<MatchEvent>{
    private String eventId;
    private Long eventTime;  //in minutes
    private Boolean isHomeEvent;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public Boolean isHomeEvent() {
        return isHomeEvent;
    }

    public void setHomeEvent(Boolean homeEvent) {
        isHomeEvent = homeEvent;
    }

    @Override
    public int compareTo(MatchEvent another) {
        int result = this.eventTime.compareTo(another.eventTime);
        return result;
    }
}
