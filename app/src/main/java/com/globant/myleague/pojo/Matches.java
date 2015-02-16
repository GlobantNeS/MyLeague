package com.globant.myleague.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class Matches {
    @SerializedName("id_tor")
    private String id;
    @SerializedName("id_local")
    private String idLocal;
    @SerializedName("id_visit")
    private String idVisit;
    @SerializedName("date_match")
    private String dateMatch;
    @SerializedName("local_score")
    private String localScore;
    @SerializedName("local_faults")
    private String localFaults;
    @SerializedName("local_exp")
    private String localExp;
    @SerializedName("visit_score")
    private String visitScore;
    @SerializedName("visit_faults")
    private String visitFaults;
    @SerializedName("visit_exp")
    private String visitExp;
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdVisit() {
        return idVisit;
    }

    public void setIdVisit(String idVisit) {
        this.idVisit = idVisit;
    }

    public String getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(String dateMatch) {
        this.dateMatch = dateMatch;
    }

    public String getLocalScore() {
        return localScore;
    }

    public void setLocalScore(String localScore) {
        this.localScore = localScore;
    }

    public String getLocalFaults() {
        return localFaults;
    }

    public void setLocalFaults(String localFaults) {
        this.localFaults = localFaults;
    }

    public String getLocalExp() {
        return localExp;
    }

    public void setLocalExp(String localExp) {
        this.localExp = localExp;
    }

    public String getVisitScore() {
        return visitScore;
    }

    public void setVisitScore(String visitScore) {
        this.visitScore = visitScore;
    }

    public String getVisitFaults() {
        return visitFaults;
    }

    public void setVisitFaults(String visitFaults) {
        this.visitFaults = visitFaults;
    }

    public String getVisitExp() {
        return visitExp;
    }

    public void setVisitExp(String visitExp) {
        this.visitExp = visitExp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}