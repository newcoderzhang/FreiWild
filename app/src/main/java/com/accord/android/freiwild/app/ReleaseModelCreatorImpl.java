package com.accord.android.freiwild.app;


public class ReleaseModelCreatorImpl implements ModelCreator {
    @Override
    public Model<Release> createModel(RequestsApi requestsApi) {
        return new ReleaseModelImpl(requestsApi);
    }
}
