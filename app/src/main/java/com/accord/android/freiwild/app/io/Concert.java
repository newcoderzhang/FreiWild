package com.accord.android.freiwild.app.io;


import com.google.gson.annotations.SerializedName;

public class Concert implements Data {

    @SerializedName("ID")
    public String id;

    @SerializedName("Kategorie")
    public String category;

    @SerializedName("Title")
    public String title;

    @SerializedName("Datum")
    public String date;

    @SerializedName("Ort")
    public String city;

    @SerializedName("Location")
    public String location;

    @SerializedName("Adresse")
    public String address;

    @SerializedName("Beginn")
    public String beginTime;

    @SerializedName("Laengengrad")
    public String longitude;

    @SerializedName("Breitengrad")
    public String latitude;

    @SerializedName("Bemerkung")
    public String comment;

    @SerializedName("DoorsOpen")
    public String doorsOpen;

    @SerializedName("SoldOut")
    public String soldOut;

    @SerializedName("ShopLink")
    public String shopUrl;

    @SerializedName("FlyerURL")
    public String flyerUrl;

    @SerializedName("ImageURL")
    public String flagUrl;
}
