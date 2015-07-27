package com.accord.android.freiwild.app;


import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface RequestsApi {

    @GET("/read/Releases/")
    Observable<List<Release>> getReleases();
}
