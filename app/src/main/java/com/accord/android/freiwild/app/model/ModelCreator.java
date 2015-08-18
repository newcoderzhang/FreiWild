package com.accord.android.freiwild.app.model;


import com.accord.android.freiwild.app.io.Data;

public interface ModelCreator<T extends Data> {
    Model<T> createModel();
}
