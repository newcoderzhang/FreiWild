package com.accord.android.freiwild.app;


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
                .flatMap(new Func1<List<Release>, Observable<Release>>() {
                    @Override
                    public Observable<Release> call(List<Release> releases) {
                        return Observable.from(releases);
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
