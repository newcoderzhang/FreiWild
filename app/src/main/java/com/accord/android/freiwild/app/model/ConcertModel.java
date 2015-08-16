package com.accord.android.freiwild.app.model;

import com.accord.android.freiwild.app.RequestsApi;
import com.accord.android.freiwild.app.io.Concert;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ConcertModel implements Model<Concert> {

    private final RequestsApi mRequestApi;

    public ConcertModel(RequestsApi requestsApi) {
        mRequestApi = requestsApi;
    }

    @Override
    public Observable<Concert> getData() {
        return mRequestApi.getConcerts()
                .flatMap(new Func1<List<Concert>, Observable<Concert>>() {
                    @Override
                    public Observable<Concert> call(List<Concert> concerts) {
                        return Observable.from(concerts);
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
