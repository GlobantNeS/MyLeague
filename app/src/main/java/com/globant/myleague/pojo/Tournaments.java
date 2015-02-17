package com.globant.myleague.pojo;

import com.google.gson.annotations.SerializedName;

public class Tournaments {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("dateini")
    private String dateini;
    @SerializedName("num_teams")
    private String numTeams;
    @SerializedName("period")
    private String period;
    @SerializedName("prices")
    private String prices;
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateini() {
        return dateini;
    }

    public void setDateini(String dateini) {
        this.dateini = dateini;
    }

    public String getNumTeams() {
        return numTeams;
    }

    public void setNumTeams(String numTeams) {
        this.numTeams = numTeams;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString()
    {
        return name+"\n\t\t"+dateini;
    }
}
