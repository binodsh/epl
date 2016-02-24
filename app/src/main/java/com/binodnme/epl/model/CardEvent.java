package com.binodnme.epl.model;

/**
 * Created by binodnme on 2/24/16.
 */
public class CardEvent extends MatchEvent{
    private String type;
    private MatchTeamPlayerDetail player;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MatchTeamPlayerDetail getPlayer() {
        return player;
    }

    public void setPlayer(MatchTeamPlayerDetail player) {
        this.player = player;
    }
}
