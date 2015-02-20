package com.globant.myleague.pojo;

import com.google.gson.annotations.SerializedName;

public class Tournaments {
    private static final String DEFAULT_IMAGE_TOURNAMENT = "http://www.kickleuk.org/wp-content/uploads/2014/01/golden-trophy-cup-soccer-ball-19818804.jpg";
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
    @SerializedName("url_image")
    private String url_image;

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


    public String toString()
    {
        return name+"\n\t\t"+dateini;
    }

    public String getUrl_image() {
        if(url_image == null)
            return DEFAULT_IMAGE_TOURNAMENT;
        else
            if(url_image.isEmpty())
                return DEFAULT_IMAGE_TOURNAMENT;
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
