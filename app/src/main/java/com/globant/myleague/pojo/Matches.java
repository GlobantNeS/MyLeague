package com.globant.myleague.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Matches extends News implements Parcelable {

    @SerializedName("id_tor")
    private String id;
    @SerializedName("id_local")
    private String idLocal;
    @SerializedName("name_local")
    private String nameLocal;
    @SerializedName("id_visit")
    private String idVisit;
    @SerializedName("name_visit")
    private String nameVisit;
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
    @SerializedName("url_img_local")
    private String urlImgLocal;
    @SerializedName("url_img_visit")
    private String urlImgVisit;

    public Creator<Matches> CREATOR = new Creator<Matches>() {
        @Override
        public Matches createFromParcel(Parcel source) {
            return new Matches(source);
        }

        @Override
        public Matches[] newArray(int size) {
            return new Matches[size];
        }
    };

    public Matches(){

    }

    public Matches(Parcel matches){
        id = matches.readString();
        idLocal =  matches.readString();
        nameLocal = matches.readString();
        idVisit = matches.readString();
        nameVisit = matches.readString();
        dateMatch = matches.readString();
        localScore = matches.readString();
        localFaults = matches.readString();
        localExp = matches.readString();
        visitScore = matches.readString();
        visitFaults = matches.readString();
        visitExp = matches.readString();
        urlImgLocal = matches.readString();
        urlImgVisit = matches.readString();
    }


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

    public String getNameLocal() {
        return nameLocal;
    }

    public void setNameLocal(String nameLocal) {
        this.nameLocal = nameLocal;
    }

    public String getNameVisit() {
        return nameVisit;
    }

    public void setNameVisit(String nameVisit) {
        this.nameVisit = nameVisit;
    }

    public String getUrlImgLocal() {
        return urlImgLocal;
    }

    public void setUrlImgLocal(String urlImgLocal) {
        this.urlImgLocal = urlImgLocal;
    }

    public String getUrlImgVisit() {
        return urlImgVisit;
    }

    public void setUrlImgVisit(String urlImgVisit) {
        this.urlImgVisit = urlImgVisit;
    }

    @Override
    public String toString() {
        return "Local team=" + nameLocal + "Visit Team: "+ nameVisit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idLocal);
        dest.writeString(nameLocal);
        dest.writeString(idVisit);
        dest.writeString(nameVisit);
        dest.writeString(dateMatch);
        dest.writeString(localScore);
        dest.writeString(localFaults);
        dest.writeString(localExp);
        dest.writeString(visitScore);
        dest.writeString(visitFaults);
        dest.writeString(visitExp);
        dest.writeString(urlImgLocal);
        dest.writeString(urlImgVisit);
    }
}
