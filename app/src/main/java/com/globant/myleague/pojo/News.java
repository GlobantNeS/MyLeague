package com.globant.myleague.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 17/02/15.
 */
public class News {

    private static final String DEFAULT_IMAGE_NEWS = "http://fortyfort.org/images/news.jpg";

    @SerializedName("title_news")
    private String titleNews;
    @SerializedName("id_new")
    private String idNews;
    @SerializedName("url_picture")
    private String urlPicture;
    @SerializedName("description_news")
    private String descriptionNews;

    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public String getUrlPicture() {
        if(urlPicture == null)
            return DEFAULT_IMAGE_NEWS;
        else
            if(urlPicture.isEmpty())
                return DEFAULT_IMAGE_NEWS;
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getDescriptionNews() {
        return descriptionNews;
    }

    public void setDescriptionNews(String descriptionNews) {
        this.descriptionNews = descriptionNews;
    }

    public String getTitleNews() {
        return titleNews;
    }


}
