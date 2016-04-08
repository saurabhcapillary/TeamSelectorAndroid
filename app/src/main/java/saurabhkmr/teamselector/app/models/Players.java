package saurabhkmr.teamselector.app.models;

/**
 * Created by saurabhkmr on 27/3/16.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author saurabh
 */
public class Players implements Serializable {

    private long id;
    private String name;
    private long squadId;
    private long countryId;
    private String countryName;
    private String squadName;
    private boolean homeSquad;
    private boolean selected;
    private boolean captain;
    private long matchId;
    private long userId;

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

    public long getSquadId() {
        return squadId;
    }

    public void setSquadId(long squadId) {
        this.squadId = squadId;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getSquadName() {
        return squadName;
    }

    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }

    public boolean isHomeSquad() {
        return homeSquad;
    }

    public void setHomeSquad(boolean homeSquad) {
        this.homeSquad = homeSquad;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isCaptain() {
        return captain;
    }

    public void setCaptain(boolean captain) {
        this.captain = captain;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

