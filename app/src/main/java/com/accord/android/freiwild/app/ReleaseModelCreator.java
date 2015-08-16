package com.accord.android.freiwild.app;


public class ReleaseModelCreator implements ModelCreator {
    @Override
    public Model<Release> createModel(RequestsApi requestsApi) {
        return new ReleaseModel(requestsApi);
    }
}
