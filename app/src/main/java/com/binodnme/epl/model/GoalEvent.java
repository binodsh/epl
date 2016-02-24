package com.binodnme.epl.model;

/**
 * Created by binodnme on 2/24/16.
 */
public class GoalEvent extends MatchEvent {
    private String matchStatus;
    private String type;
    private String score;
    private long teamId;
    private long teamIdInternal;
    private MatchTeamPlayerDetail player;

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getTeamIdInternal() {
        return teamIdInternal;
    }

    public void setTeamIdInternal(long teamIdInternal) {
        this.teamIdInternal = teamIdInternal;
    }

    public MatchTeamPlayerDetail getPlayer() {
        return player;
    }

    public void setPlayer(MatchTeamPlayerDetail player) {
        this.player = player;
    }
}
