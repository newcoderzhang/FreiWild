package com.accord.android.freiwild.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.accord.android.freiwild.app.io.Data;
import com.accord.android.freiwild.app.model.Model;


public abstract class BaseWorkerFragment<T extends Data> extends Fragment {

    protected static RequestsApi mRequestApi;

    public BaseWorkerFragment() {
        if (mRequestApi == null) {
            mRequestApi = ServiceGenerator.createService(RequestsApi.class);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public abstract Model<T> getModel();
}
