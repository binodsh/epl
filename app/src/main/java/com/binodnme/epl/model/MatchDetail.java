package com.binodnme.epl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by binodnme on 2/22/16.
 */
public class MatchDetail implements Serializable {
    private long matchId;
    private Date kickoffTime;
    private int minute;
    private String stadiumName;
    private String refereeName;
    private String matchStatus;
    private int homeTeamScore;
    private int awayTeamScore;
    private MatchTeamDetail homeTeam;
    private MatchTeamDetail awayTeam;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Date getKickoffTime() {
        return kickoffTime;
    }

    public void setKickoffTime(Date kickoffTime) {
        this.kickoffTime = kickoffTime;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public MatchTeamDetail getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(MatchTeamDetail homeTeam) {
        this.homeTeam = homeTeam;
    }

    public MatchTeamDetail getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(MatchTeamDetail awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
