package com.globant.myleague.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created the first version by kaineras on 17/02/15.
 */
public class TeamsInTournaments {
    @SerializedName("id_tor")
    private String idTournament;
    @SerializedName("id_team")
    private String idTeam;


    public String getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(String idTournament) {
        this.idTournament = idTournament;
    }

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }
}
