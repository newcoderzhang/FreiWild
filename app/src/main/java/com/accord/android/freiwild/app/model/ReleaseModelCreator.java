package com.accord.android.freiwild.app.model;


import com.accord.android.freiwild.app.RequestsApi;
import com.accord.android.freiwild.app.io.Release;

public class ReleaseModelCreator implements ModelCreator {
    @Override
    public Model<Release> createModel(RequestsApi requestsApi) {
        return new ReleaseModel(requestsApi);
    }
}
