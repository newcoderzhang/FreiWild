package com.accord.android.freiwild.app;


import com.google.gson.annotations.SerializedName;

public class Release implements Data {

    @SerializedName("ID")
    public String id;

    @SerializedName("Title")
    public String title;

    @SerializedName("Text")
    public String htmlText;

    @SerializedName("ReleaseDate")
    public String releaseDate;

    @SerializedName("ImageURL")
    public String imageUrl;

    @SerializedName("iTunesLink")
    public String itunes;

    @SerializedName("GooglePlayLink")
    public String googlePlay;

    @SerializedName("WindowsStoreLink")
    public String windowsStore;

    @SerializedName("AmazonLing")
    public String amazon;

    @SerializedName("EMPLink")
    public String emp;

    @SerializedName("SongtextLink")
    public String songText;
}
