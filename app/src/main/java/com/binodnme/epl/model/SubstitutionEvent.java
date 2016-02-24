package com.binodnme.epl.model;

/**
 * Created by binodnme on 2/24/16.
 */
public class SubstitutionEvent extends MatchEvent {
    private MatchTeamPlayerDetail playerIn;
    private MatchTeamPlayerDetail playerOut;

    public MatchTeamPlayerDetail getPlayerIn() {
        return playerIn;
    }

    public void setPlayerIn(MatchTeamPlayerDetail playerIn) {
        this.playerIn = playerIn;
    }

    public MatchTeamPlayerDetail getPlayerOut() {
        return playerOut;
    }

    public void setPlayerOut(MatchTeamPlayerDetail playerOut) {
        this.playerOut = playerOut;
    }
}
