package com.example.onesquare.model;

import java.net.URL;
import java.util.Date;

/**
 * Created by francis on 03/28/15.
 */
public class CheckIn {
    private Date date;
    private String address;
    private String place;
    private URL url;
    private URL pictureURL;
    private boolean isFavorite;

    public CheckIn(Date date, String address, String place, URL url,
                   URL pictureURL, boolean isFavorite) {
        this.date = date;
        this.address = address;
        this.place = place;
        this.url = url;
        this.pictureURL = pictureURL;
        this.isFavorite = isFavorite;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(URL pictureURL) {
        this.pictureURL = pictureURL;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return address + " " + place;
    }
}
