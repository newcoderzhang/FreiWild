package com.accord.android.freiwild.app.model;


import com.accord.android.freiwild.app.RequestsApi;
import com.accord.android.freiwild.app.io.Release;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ReleaseModel implements Model<Release> {

    private final RequestsApi mRequestApi;

    public ReleaseModel(RequestsApi requestsApi) {
        mRequestApi = requestsApi;
    }

    @Override
    public Observable<Release> getData() {
        return mRequestApi.getReleases()
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.io());
    }
}
