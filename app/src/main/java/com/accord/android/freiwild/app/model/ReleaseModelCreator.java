package com.accord.android.freiwild.app.model;


import com.accord.android.freiwild.app.RequestsApi;
import com.accord.android.freiwild.app.ServiceGenerator;
import com.accord.android.freiwild.app.io.Release;

public class ReleaseModelCreator implements ModelCreator<Release> {
    @Override
    public ReleaseModel createModel() {
        ServiceGenerator.setDateFormat("dd.MM.yyyy");
        return new ReleaseModel(ServiceGenerator.createService(RequestsApi.class));
    }
}
