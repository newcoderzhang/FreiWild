package com.accord.android.freiwild.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.accord.android.freiwild.app.io.Data;
import com.accord.android.freiwild.app.model.Model;


public abstract class BaseWorkerFragment<T extends Data> extends Fragment {

    public BaseWorkerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public abstract Model<T> getModel();
}
