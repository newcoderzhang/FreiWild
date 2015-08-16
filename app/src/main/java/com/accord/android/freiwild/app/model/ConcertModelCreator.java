package com.accord.android.freiwild.app.model;


import com.accord.android.freiwild.app.RequestsApi;
import com.accord.android.freiwild.app.io.Concert;

public class ConcertModelCreator implements ModelCreator {
    @Override
    public Model<Concert> createModel(RequestsApi requestsApi) {
        return new ConcertModel(requestsApi);
    }
}
