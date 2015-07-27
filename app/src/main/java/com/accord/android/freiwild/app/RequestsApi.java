package com.accord.android.freiwild.app;


import retrofit.http.GET;
import rx.Observable;

public interface RequestsApi {

    @GET("/read/Releases/")
    Observable<Release> getReleases();
}
