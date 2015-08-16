package com.accord.android.freiwild.app;


public interface ModelCreator<T extends Data> {
    Model<T> createModel(RequestsApi requestsApi);
}
