package com.binodnme.epl.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by binodnme on 2/22/16.
 */
public class MatchTeamDetail implements Serializable {
    private long teamId;
    private String teamName;
    private List<MatchTeamPlayerDetail> startingLineUps;
    private List<MatchTeamPlayerDetail> substitutes;

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<MatchTeamPlayerDetail> getStartingLineUps() {
        return startingLineUps;
    }

    public void setStartingLineUps(List<MatchTeamPlayerDetail> startingLineUps) {
        this.startingLineUps = startingLineUps;
    }

    public List<MatchTeamPlayerDetail> getSubstitutes() {
        return substitutes;
    }

    public void setSubstitutes(List<MatchTeamPlayerDetail> substitutes) {
        this.substitutes = substitutes;
    }
}
