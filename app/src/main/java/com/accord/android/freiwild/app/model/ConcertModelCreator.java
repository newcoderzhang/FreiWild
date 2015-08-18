package com.accord.android.freiwild.app.model;


import com.accord.android.freiwild.app.RequestsApi;
import com.accord.android.freiwild.app.ServiceGenerator;
import com.accord.android.freiwild.app.io.Concert;

public class ConcertModelCreator implements ModelCreator<Concert> {
    @Override
    public ConcertModel createModel() {
        ServiceGenerator.setDateFormat("dd.MM.yyyy");
        return new ConcertModel(ServiceGenerator.createService(RequestsApi.class));
    }
}
