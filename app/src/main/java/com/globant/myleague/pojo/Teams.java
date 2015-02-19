package com.globant.myleague.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Teams implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("manager")
    private String manager;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("url_image")
    private String urlimage;

    private boolean selected = false;

    public static Creator<Teams> CREATOR = new Creator<Teams>() {
        @Override
        public Teams createFromParcel(Parcel source) {
            return new Teams(source);
        }

        @Override
        public Teams[] newArray(int size) {
            return new Teams[size];
        }
    };

    public Teams() {

    }

    public Teams (Parcel team) {
        id = team.readString();
        name = team.readString();
        manager = team.readString();
        email = team.readString();
        phone = team.readString();
        urlimage = team.readString();
    }



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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(manager);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(urlimage);
    }
}
