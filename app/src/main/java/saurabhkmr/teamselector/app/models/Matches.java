package saurabhkmr.teamselector.app.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by saurabhkmr on 22/3/16.
 */
public class Matches implements Serializable {

    private long id;
    private String name;
    private String venue;
    private String date;
    private String homeTeam;
    private String awayTeam;

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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public static int getId(String squadName){
        switch (squadName.toLowerCase()){
            case "mi":
                return 6;
            case "dd":
                return 4;
            case "rcb":
                return 3;
            case "srh":
                break;
            case "kx1p":
                return 5;
            case "kkr":
                return 1;
            case "gl":
                return 7;
            case "rps":
                return 8;
            default:break;
        }
        return 0;
    }
}
