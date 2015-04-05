package com.example.onesquare.model;

import android.net.Uri;

import java.net.URL;
import java.util.Date;

/**
 * Created by francis on 03/28/15.
 */
public class CheckIn {
    private Date date;
    private String address;
    private String place;
    private Uri url;
    private Uri pictureURL;
    private boolean isFavorite;
    private int id;

    public CheckIn(int id, Date date, String address, String place, Uri url,
                   Uri pictureURL, boolean isFavorite) {
        this.id = id;
        this.date = date;
        this.address = address;
        this.place = place;
        this.url = url;
        this.pictureURL = pictureURL;
        this.isFavorite = isFavorite;
    }

    public CheckIn(Date date, String address, String place, Uri url,
                   Uri pictureURL, boolean isFavorite) {
        this(-1, date, address, place, url, pictureURL, isFavorite);
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

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public Uri getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(Uri pictureURL) {
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

    public int getId() {
        return id;
    }

    public static CheckIn create(String place, String address, Date date, Uri url,
                                 Uri pictureUrl, boolean isFavorite) {
        return new CheckIn(date, address, place, url, pictureUrl, isFavorite);
    }
}
